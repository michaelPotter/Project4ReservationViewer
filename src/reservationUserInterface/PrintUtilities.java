/**
 * PrintUtilities.java
 */
package reservationUserInterface;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* <pre>
* Class: PrintUtilities
* Description: A simple utility class that lets you very simply print
*  an arbitrary component.
* @author: Weston, Michael, Vincent
* Environment: PC, Windows 7, Windows 8, NetBeans 7.4
* Date: 6.15.2014
* @version 2.0
* </pre>
*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class PrintUtilities implements Printable
{
  private Component componentToBePrinted;

  /**
   * initializes the print
   * @param c component to be printed
   */
  public static void printComponent(Component c)
  {
    new PrintUtilities(c).print();
  }
  /**
   * Constructor to pass in component
   * @param componentToBePrinted the component to be printed
   */
  public PrintUtilities(Component componentToBePrinted)
  {
    this.componentToBePrinted = componentToBePrinted;
  }
  
  /**
   * Print the component passed into the constructor
   */
  public void print() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(this);
    if (printJob.printDialog())
      try {
        printJob.print();
      } catch(PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
  }

  /**
   * Print the page
   * @param g the graphic for the print.
   * @param pageFormat the page format of the printing preference
   * @param pageIndex An integer telling the index of the page
   * @return boolean page exists or not
   */
  @Override
  public int print(Graphics g, PageFormat pageFormat, int pageIndex)
  {
    if (pageIndex > 0) {
      return(NO_SUCH_PAGE);
    } else {
      Graphics2D g2d = (Graphics2D)g;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
      disableDoubleBuffering(componentToBePrinted);
      componentToBePrinted.paint(g2d);
      enableDoubleBuffering(componentToBePrinted);
      return(PAGE_EXISTS);
    }
  }

  /** 
   *  The speed and quality of printing suffers dramatically if
   *  any of the containers have double buffering turned on.
   *  @param c A component parameter
   *  So this turns if off globally.
   *  @see enableDoubleBuffering
   */
  public static void disableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(false);
  }

  
  /**
   * Re-enables double buffering globally. 
   * @param c A component parameter
   */
  public static void enableDoubleBuffering(Component c) {
    RepaintManager currentManager = RepaintManager.currentManager(c);
    currentManager.setDoubleBufferingEnabled(true);
  }
}
