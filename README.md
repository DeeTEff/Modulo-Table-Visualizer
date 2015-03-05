# Modulo-Table-Visualizer
Reads in files containing modulo tables and generates pixels in a grid.

# Overview
-----------------------------------------------------------
This program takes modulo tables and converts the numbers to pixels based on range.
The provided sample files show the proper format.

To display a modulo table, click Open Modulo Table and select the desired .mod file.
The image will be loaded to the right of the options.

The Modulo Tables tab allows the user to select a working directory and quickly change between modulo tables.

To save a modulo table as .png, click save modulo table and whatever is currently drawn on the screen will be saved as a .png in a directory of your choice.

Currently, this program is limited to modulo tables with max values < 1000

# Settings
-----------------------------------------------------------
Randomize Colors:

This option generates a random seed for the base value of the table to be multiplied by, and is run each time the frame is redrawn.

Repetition:

Repeats the image x times in a tile pattern.

Scale Multiplier:

Repeats each pixel x times to enlarge the image.
