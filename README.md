# Student Ranking System

A console application in Java that ranks students by marks. Data Structures and Algorithms (DSA) project, Rungta International Skills University.

**Concepts used:** Array, Sorting, Searching, Methods, File Handling

## Features

| Option | What it does |
|---|---|
| 1 - Add Student | Adds a student with marks (0 to 100); roll numbers start at R101 |
| 2 - Display Rank List | Shows students in descending order of marks |
| 3 - Search | Finds a student by roll number or name and shows the rank |
| 4 - Show Topper | Shows the student with the highest marks |
| 5 - Save and Exit | Saves all records to `students.txt` |

Sorting (insertion sort) and searching (linear search) are written manually, without built-in sort or search methods. Records are loaded from `students.txt` at start and saved on exit.

## How to run

Requires JDK 21.

```
javac StudentRankingSystem.java
java StudentRankingSystem
```

Or directly: `java StudentRankingSystem.java`

Use option 5 to exit, so that the data is saved.

## Sample

```
Student added. Roll: R101 | Priya Singh | 88
Student added. Roll: R102 | Arjun Das | 93

Rank 1: R102 | Arjun Das | 93
Rank 2: R101 | Priya Singh | 88

Found: R101 | Priya Singh | 88 | Rank: 2
```

Marks outside 0 to 100 give: `Invalid marks. Marks must be between 0 and 100.`

## Folder structure

```
StudentRankingSystem.java      main program
report/                        interim report (PDF and Word)
presentation/                  project presentation (PDF)
demo/student_ranking_demo.html browser demo of the same logic
```

## Author

Juhi Kumari (ERP ID: RU-25-10629)
Guide: Naina Singh
