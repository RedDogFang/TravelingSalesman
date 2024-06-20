# TravelingSalesman

To build this code you will need to download the 3.2.2 version of jchart2d. 
jChart2D can be found at https://jchart2d.sourceforge.net/download.shtml
After downloading the file extract it to the jchart2d directory in this
repository.

During the extraction there should be some files that are duplicates.
Overwrite or skip these files. They are for the license and notice

After extracting these files edit the .classpath file in this project
to point to the locaction on your machine where the jchart2d directory exist

<classpathentry kind="lib" path="../jchart2d/jchart2d-3.2.2.jar"/>

This line works for me but does not work for all. Replace the .. with
the full path for this directory. Here is an example 

<classpathentry kind="lib" path="C:/workspace/SalesmansDilema2/jchart2d/jchart2d-3.2.2.jar"/>

At this point the project should build and run. It should pop up one 
window with a number of straight lines which is a quick solution to the 
traveling salesman problem.