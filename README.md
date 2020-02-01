# Treasure Hunt

This is a command line program written using Gradle and Java 11 which performs a treasure hunt.

This program can be run with the steps below:

1. Go into the directory of this project using command line.
1. Install the program with `gradlew clean install`
1. Run the treasure hunt command using `./build/install/TreasureHunt/bin/TreasureHunt --help`

### Input File Format

<ul>
  <li>Number of ways to travel (format: {integer}).</li>
  <li>Approximations on speeds for different ways of travel (format: {string},{integer}mph).</li>
  <li>Number of directions (format: {integer}).</li>
  <li>Directions to take to locate the treasure from origin (format: {string},{Time},{Direction})."</li>
</ul>

Where:

```
{string} = string
{integer} = integer

{Time} = One of strings:
  {integer} days [{integer} hours [{integer} mins]],
  {integer} hours [{integer} mins],
  {integer} mins

{Direction} = One of strings: N,S,E,W,NW,NE,SW,SE
```

### Example input file:

```$xslt
5
Walk,3mph
Run,6mph
Horse Trot,4mph
Horse Gallop,15mph
Elephant Ride,6mph
7
Walk,32 days 20 mins,N
Run,1 hour,E
Horse Trot,3 hours,NW
Elephant Ride,1 hour 30 mins,SE
Horse Gallop,1 day 20 mins,SE
Walk,30 mins,SW
Horse Trot,1 hour 1 min,W
```

