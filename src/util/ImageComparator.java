package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageComparator {

    /**
     * Method to compare similarity of the images and generate output formatted lists
     * @param images
     * @return
     */
    public List<List<String>> compare(List<List<String>> images) {
        List<List<String>> result = new ArrayList<>();

        for (List<String> pair : images) {
            List<String> output = new ArrayList<>(pair);
            try {
                BufferedImage image1 = ImageIO.read(new File(pair.get(0)));
                BufferedImage image2 = ImageIO.read(new File(pair.get(1)));
                long start = System.nanoTime();     // start timestamp
                String similar = Double.toString(hashSimilarity(image1, image2));
                // Preferred integer for zero
                if (similar.equals("0.0")) {
                    similar = "0";
                }
                long finishElapsed = System.nanoTime() - start;     // time elapsed in long
                Double timeElapsed = (double) (finishElapsed / 1_000_000.0); // convert to millisecond

                output.add(similar);
                output.add(Double.toString(timeElapsed));

                result.add(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Calculate similarity
     * @param image1
     * @param image2
     * @return
     */
    private double hashSimilarity(BufferedImage image1, BufferedImage image2) {

        String fingerprint1 = this.hashImage(image1);
        String fingerprint2 = this.hashImage(image2);
        char [] ch1 = fingerprint1.toCharArray();   // convert to character array
        char [] ch2 = fingerprint2.toCharArray();

        int diffBit = 0;
        for (int i = 0; i < 64; i++) {
            if (ch1[i] != ch2[i]) {
                diffBit++;
            }
        }
        return (double) diffBit / 64.0;
    }

    /**
     * Generate fingerprint
     * @param image
     * @return
     */
    private String hashImage(BufferedImage image) {
        int width = 8, height = 8;
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        scaledImage.createGraphics()    // graphics
                .drawImage(image, 0, 0, width, height ,null);

        // first calculate the average gray scale
        int total = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                total += this.convertToGray(image.getRGB(i, j));
            }
        }

        // Generate the string fingerprint
        StringBuffer res = new StringBuffer();
        int avg = total / (width * height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = image.getRGB(i, j);
                int gray = this.convertToGray(pixel);

                if (gray >= avg) {
                    res.append("1");
                } else {
                    res.append("0");
                }
            }
        }
        return res.toString();
    }

    /**
     * Simplify the rgb to gray scales
     * @param rgb
     * @return
     */
    private int convertToGray(int rgb) {
        int a = rgb & 0xff000000;
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = rgb & 0xff;
        rgb = (r * 77 + g *151 + b * 28) >> 8;
        return a | (rgb << 16) | (rgb << 8) | rgb;
    }
}
