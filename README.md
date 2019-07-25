### Please read carefully before using the tool.

To compile and run the software, use command:
```
make run
```

To clean executives, run command:
```
make clean
```
The main UI will pop out. User will run open to select a file from file chooser (csv only), and then press save to save the result. If you have any trouble running the software, please email [Jiayi Liu](jiayi.liu@hotmail.com)

# Features
The software will take a csv file input which contains two columns : the absolute path of imageA, the absolute path of imageB (separated by comma). Then it calculates the similarity of the images (where 0 is same, 1 is totally different), and output the result to a csv file containing four columns:the absolute path of imageA, the absolute path of imageB, similarity, time elapsed.

The software will show a simple UI which is self-explained.

# Design
Implement the design with MVC, where controller is the buttons inserted in the UI View, and model stores data structures and provide useful APIs.

## Terminology
1. Updator. A class that manage the update of the software.
2. CSVManager. A class which manage read and writing to csv file.
3. ImageComparator. A class containing all algorithms to compare the similarity of two images.

In general, the main function is only responsible to setup MVC. It should be as simple as possible. Model has a CSVManager, an Updator, and an ImageComparator. View is responsible to refresh UI.

# Product Mangement
## Phase 1 - finished
### Mission

Design a simple UI with MVC. Implement core functions using *__Perceptual Hashing Algorithm__*. Here is a tutorial presented by _Jens Segers on Dec 14 2014_
[Perceptual Image Hash](https://jenssegers.com/61/perceptual-image-hashes)



### Obstacles
- How to compare the similarity of two images?

  1. First way: Convert the images into bitmaps. Goal is to compare the images bit by bit. Problem: there will be a inner for loop inside a loop (runtime = O(height*width)). Memory may easily leak if the images are big.
  2. Second way: Use Opencv libraries. This method is more accurate, but relies on the external library. The efficiency of the program would be affected.
  3. __Best way__: Perceptual Hashing Algorithm. This includes 4 steps: reducing size (size insensitive), reducing color (color insensitive), calculating the average color, calculating the hash (fingerprint). 



- Read and write csv files

    It is a bad idea to check the format of a csv file through our software. And often, it is an impossible task. Thus, user must ensure that the input file is correctly written. Sample format:

    >*~/Desktop/Images/imageA.png,~/Desktop/Images/imageB.png*

    where path must be in absolute path, images exist (otherwise an IOException will be thrown), and paths are joint by a comma delimiter.

    Sample output csv:

    >*~/Desktop/Images/imageA.png,~/Desktop/Images/imageB.png,0,1.342*

    where the first two columns are the same as the input. The third column shows the similarity of the two images. The last column contains the time elapsed.


- How to make sure our software will work?
  
  Testing is the best way to examine the functionality of a software. According to the design, we use UI to test our program.

- How to make sure the user can use the software with no problem?

   Requirement engineers may vote for a sixty-page-long user manual, which introduces use cases,features, terminologies etc. However, not a single user wants to read lengthy manuals. Therefore, we need a UI that is complete and concise. Complete means it contains all the features the user needs. Concise means it only shows neceesary buttons. Additional ambiguity should be avoided.

- How to make sure the maintainers will succeed?

  1. Good documentions and code comments are the best way we communicate.
  2. Please set up a meeting with me if the maintainer has any problem.
  3. Design a SW with "**Low coupling and high cohesion**". On the one hand, it helps programmers understand structures and patterns. On the other hand, if maintainer just needs to change one project odule, the other parts are still functional.



## Phase 2 - in discussion
### Mission
Implement software update functions. This may require additional servers. Technologies may include Spring Boot, docker etc.

### Obstacles

- How to make sure users are using the latest version?

  1. One way is leaving the task to ITs, so that new version will be pushed manually. Unnecessary expense.
  2. Second way is limiting the lifecycle of our product. For example, we can issue a license which expires every 30 days. Users have to request a new version every 30 days. Problem: We are not sure how often the SW will be updated.
  3. The **best way** is adding a "Self-check" feature in our product. We need a server that takes requests from our SW (on start) and replies with the latest version number. This can be done using Spring Boot and docker. Our product will regularly check if its version is out-of-date. Keep the function of the server simple, so we don't need extra cost.
   

- Improve responsiveness
  
  The current UI is a very simple version. We need to add some prompt which shows the calculating process, so that users will know when to click save. This is not implemented in this project 

***