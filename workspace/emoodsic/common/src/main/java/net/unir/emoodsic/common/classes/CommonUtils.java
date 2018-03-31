/**
 * 
 */
package net.unir.emoodsic.common.classes;

import java.awt.geom.Point2D;

/**
 * @author Álvaro
 *
 */
public final class CommonUtils {

    /**
     * @return A string with name of the caller method
     */
    public static String getMethodName() {
        return new EmoodsicException().getStackTrace()[1].getMethodName();
    }
    
    /**
     * @return A string with the full stack trace starting with the caller method name
     */
    public static String getStackTrace() {
        final EmoodsicException emex = new EmoodsicException();
        int index = 2;
        final StringBuilder method = new StringBuilder(emex.getStackTrace()[1].getMethodName());
        while ((index < emex.getStackTrace().length)
                && (emex.getStackTrace()[index].getLineNumber() > 0)) {
            method.append("." + emex.getStackTrace()[index].getMethodName());
            index += 1;
        }
        return method.toString();
    }
    
    /**
     * 
     * @param ax
     * @param ay
     * @param bx
     * @param by
     * @param px
     * @param py
     * @param clampToSegment
     * @param dest
     * @return
     * @see http://stackoverflow.com/questions/1459368/snap-point-to-a-line
     */
    public static Point2D nearestPointOnLine(double ax, double ay, double bx, double by, double px, double py,
            boolean clampToSegment, Point2D dest) {
        // Thanks StackOverflow!
        // http://stackoverflow.com/questions/1459368/snap-point-to-a-line-java
        if (dest == null) {
            dest = new Point2D.Double();
        }

        double apx = px - ax;
        double apy = py - ay;
        double abx = bx - ax;
        double aby = by - ay;

        double ab2 = abx * abx + aby * aby;
        double ap_ab = apx * abx + apy * aby;
        double t = ap_ab / ab2;
        if (clampToSegment) {
            if (t < 0) {
                t = 0;
            } else if (t > 1) {
                t = 1;
            }
        }
        dest.setLocation(ax + abx * t, ay + aby * t);
        return dest;
    }
}
