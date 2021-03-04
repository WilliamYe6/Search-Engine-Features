# Search-Engine-Features
This project implements several Java methods that allow search engines to filter out non relevant results. 

The Java methods implemented allow the search engine to

1)Crawl and Index

2)Rank

3)Search

The main objectives of this project 
1) Explore the portions of the web with a prebuilt database
2) Build an index containing keywords in every website visited
3) Analyze the structure of the webgraph determining priority of the website 
4) Implement the given analysis allowing the user to perform simple searches where they enter a query word
which returns a sorted list of the most relevant websites


Created a MergeSort algorithm which takes a HashMap as input and returns a sorted ArrayList
in decending order based on the values they map to. The time complexity of this method ran in O(n*log(n)

Created a Crawl and Index method which takes a String (representing the website url) as input. We will
then perform graph traversal through the web given a starting url by implementing the Breadth First Search algorithm
recursively. For each URL visisted, this method will update the Web Graph with the correct vertex and edges to the URL 
and optimize the word index so that keywords appear in the current URL appear on the mapping. 

Created a assignRanks method which ranks each vertex of the web graph. The URL is ranked higher depending on the amount being cited
in other URL's. If the URL is citing other URL's more often than being cited than the ranking is less favorable.


