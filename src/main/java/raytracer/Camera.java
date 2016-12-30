package raytracer;

public class Camera {

    private Point eye;
    private Vector vx;
    private Vector vy;
    private Vector vz;

    private double windowDistance;
    private double windowWidth;
    private double windowHeight;
    private Matrix rT;
    private double invCols;
    private double invRows;
//    = new Matrix(new double[][]{
//        {vx.x, vy.x, vz.x, 0},
//        {vx.y, vy.y, vz.y, 0},
//        {vx.z, vy.z, vz.z, 0},
//        {0, 0, 0, 1}
//    });
    private Matrix tInv;
    Matrix matrix;
//    = new Matrix(new double[][]{
//        {1, 0, 0, eye.x},
//        {0, 1, 0, eye.y},
//        {0, 0, 1, eye.z},
//        {0, 0, 0, 1}
//    });
    private double rows, cols;
//	private double fovy, fovx;

    public Camera(Point eye, Point center, Vector up, double fovy, int cols, int rows) {
        fovy = Math.toRadians(fovy);
        double fovx = fovy * cols / rows;

        Vector at = new Vector(eye, center);
        vz = at.negate().normalize();
        vx = up.cross(vz).normalize();
        vy = vz.cross(vx);

        this.eye = eye;
//		this.center = center;
//		this.up = up;
        this.cols = cols;
        this.rows = rows;
        invCols = 1.0/this.cols;
        invRows = 1.0/this.rows;
        windowDistance = 1.0;
        windowHeight = Math.sin(fovy / 2.0) * windowDistance * 2.0;
        windowWidth = Math.sin(fovx / 2.0) * windowDistance * 2.0;
        rT = new Matrix(new double[][]{
            {vx.x, vy.x, vz.x, 0},
            {vx.y, vy.y, vz.y, 0},
            {vx.z, vy.z, vz.z, 0},
            {0, 0, 0, 1}
        });
        tInv = new Matrix(new double[][]{
            {1, 0, 0, eye.x},
            {0, 1, 0, eye.y},
            {0, 0, 1, eye.z},
            {0, 0, 0, 1}
        });
        matrix = tInv.times(rT);
//        Log.debug("  Viewframe:");
//        Log.debug("    Org: " + eye.toString());
//        Log.debug("    X:   " + vx.toString());
//        Log.debug("    Y:   " + vy.toString());
//        Log.debug("    Z:   " + vz.toString());
//
//        Log.debug("    Window width: " + windowWidth);
//        Log.debug("          height: " + windowHeight);
    }

    public Ray getRay(int col, int row) {
        return getRay(col, row, 0.5, 0.5);
    }

    public Ray getRay(int col, int row, double pixelAdjustmentX, double pixelAdjustmentY) {
        double x = (((double) col + pixelAdjustmentX) *invCols) * windowWidth - (windowWidth *.5);
        double y = (((double) row + pixelAdjustmentY) *invRows) * windowHeight - (windowHeight *.5);

        Vector v = new Vector(eye, convertCoords(new Point(x, y, -windowDistance)));
        Ray ray = new Ray(eye, v);

        return ray;
    }

    public Point convertCoords(Point p) {
        Vector v = convertCoords(new Vector(p.x, p.y, p.z));
        return new Point(v.x, v.y, v.z);
    }

    public Vector convertCoords(Vector p) {

      
        Vector v = matrix.times(new Vector(p.x, p.y, p.z));
        return v;
    }
}
