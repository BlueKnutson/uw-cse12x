// Cooper Likens
// 05/08/2024
// CSE 123
// P1: Mini-Git
// TA: Suhani Arora
// This class creates mondrian art pieces by randomly coloring portions of a canvas.
import java.util.*;
import java.awt.*;

public class Mondrian {
    private Random random;
    
    // This method constructs the Mondrain object 
    public Mondrian() {
        this.random = new Random();
    }
    
    // This method paints the mondrian art for a basic design.
    // Parameters:
    // Color[][] pixels - The 2D array that represents the pixels for the art piece
    public void paintBasicMondrian(Color[][] pixels) {

        paintBasicMondrian(pixels, 0, 0, pixels[0].length, pixels.length);
    }

    // This is a helper method that paints the mondrian art for a basic design.
    // Parameters:
    // Color[][] pixels - The 2D array that represents the pixels for the art piece
    // int x - The current x position for the pixels on the canvas
    // int y - The current x position for the pixels on the canvas
    // int width - The current width of the pixels on the canvas
    // int height - The current height of the pixels on the canvas
    private void paintBasicMondrian(Color[][] pixels, int x, int y, int width, int height) {
        
        Color[] colors = { Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE};

        if (width < pixels[0].length / 4 && height < pixels.length / 4) {
            fill(pixels, x + 1, x + width - 1, y + 1, y + height - 1, colors);

        } else  if (height >= pixels.length / 4 && width >= pixels[0].length / 4){
            int splitWidth = 0;
            int splitHeight = 0;
            if (width > 20) {
                splitWidth = random.nextInt(width - 20) + 10;
            }
            if (height > 20) {
                splitHeight = random.nextInt(height - 20) + 10;
            }
            paintBasicMondrian(pixels, x, y, splitWidth, splitHeight);
            paintBasicMondrian(pixels, x + splitWidth, y, width - splitWidth, splitHeight);
            paintBasicMondrian(pixels, x, y + splitHeight, splitWidth, height - splitHeight);
            paintBasicMondrian(pixels, x + splitWidth, y + splitHeight, width - splitWidth, height - splitHeight);
        } else if (width >= pixels[0].length / 4 ) {
            int split = 0;
            if (width > 20) {
            split = random.nextInt(width - 20) + 10;
            }
            paintBasicMondrian(pixels, x, y, split, height);
            paintBasicMondrian(pixels, x + split, y, width - split, height);
        } else {
            int split = 0;
            if (height > 20) {
                split = random.nextInt(height - 20) + 10;
            }
            paintBasicMondrian(pixels, x, y, width, split);
            paintBasicMondrian(pixels, x, y + split, width, height - split);
        }
    }
    
    // This method fills a region of pixels with a random color from a given array
    // Parameters:
    // Color[][] pixels - The 2D array that represents the pixels for the art piece
    // int x1 - The left bound for coloring
    // int x2 - The right bound for coloring 
    // int y1 - The upper bound for coloring 
    // int y2 - The lower bound for coloring 
    // Color[] colorOption - The options for colors that can be used
    public void fill(Color[][] pixels, int x1, int x2, int y1, int y2, Color[] colorOptions) {
        int colorChoice = random.nextInt(colorOptions.length);
        for (int i = y1; i < y2; i++) {
            for (int j = x1; j < x2; j++) {
                pixels[i][j] = colorOptions[colorChoice];
            }
        }
    }

    // This method paints a complex piece of art with a complex procedure. The art will randomize
    // the amount of splits between 1 and four splits until each section is a quarter of the 
    // original canvas size.
    // Parameters:
    // Color[][] pixels - The 2D array that represents the pixels for the art piece
    public void paintComplexMondrian(Color[][] pixels) {

        paintComplexMondrian(pixels, 0, 0, pixels[0].length, pixels.length);
    }

    // This is a helper method that paints a complex piece of art with a different procedure
    // Parameters:
    // Color[][] pixels - The 2D array that represents the pixels for the art piece
    // int x - The starting x coordinate for the pixel on the board
    // int y - The starting y coordinate for the pixel on the board
    // int width - The width of the current section of the board
    // int height - The height of the current section of the board
    private void paintComplexMondrian(Color[][] pixels, int x, int y, int width, int height) {
        Color[] colors = { Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE };
    
        if (width < pixels[0].length / 4 && height < pixels.length / 4) {
            fill(pixels, x + 1, x + width - 1, y + 1, y + height - 1, colors);
        } else {
    
            int widthSplits = 1;
            int heightSplits = 1;
            if (width >= pixels[0].length / 4) {
                widthSplits += random.nextInt(4);
            }
            if (height >= pixels.length / 4) {
                heightSplits += random.nextInt(4);
            }
        
            ArrayList<Integer> splitXValues = new ArrayList<>();
            ArrayList<Integer> splitYValues = new ArrayList<>();
        
            int widthLeft = width;
            int currentX = x;
            for (int i = 0; i < widthSplits - 1; i++) {
                int maxSplitWidth = Math.max((widthLeft / (widthSplits - i)) - 20, 10);
                int splitWidth = random.nextInt(maxSplitWidth) + 10;
                currentX += splitWidth;
                splitXValues.add(currentX);
                widthLeft -= splitWidth;
            }
            splitXValues.add(x + width);
        
            int heightLeft = height;
            int currentY = y;
            for (int i = 0; i < heightSplits - 1; i++) {
                int maxSplitHeight = Math.max((heightLeft / (heightSplits - i)) - 20, 10);
                int splitHeight = random.nextInt(maxSplitHeight) + 10;
                currentY += splitHeight;
                splitYValues.add(currentY);
                heightLeft -= splitHeight;
            }
            splitYValues.add(y + height);
        
            int startX = x;
            for (int horizontalSplit : splitXValues) {
                int startY = y;
                for (int verticalSplit : splitYValues) {
                    paintComplexMondrian(pixels, startX, startY, horizontalSplit - startX, verticalSplit - startY);
                    startY = verticalSplit;
                }
                startX = horizontalSplit;
            }
        }
    }
}