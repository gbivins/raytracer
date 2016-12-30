package raytracer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    @Parameter(names = {"--infile", "-i"}, required = true)
    String inputFile;
    @Parameter(names = {"--outfile", "o"})
    String outputFile = "out.bmp";
    @Parameter(names = {"--width", "w"})
    int width = 1024;
    @Parameter(names = {"--height", "h"})
    int height = 1024;
    static final Logger logger = Logger.getLogger(Main.class.getName());
//    private static final String USAGE = "Usage:\n"
//            + "java -cp src raytracer.Main infile bmpfile width height [-options]\n"
//            + "\n"
//            + "    where:\n"
//            + "        infile    - input file name\n"
//            + "        bmpfile   - bmp output file name\n"
//            + "        width     - image width (in pixels)\n"
//            + "        height    - image hreight (in pixels)\n"
//            + //			"        -test     - run in test mode (see below)\n"+
//            //			"        -noshadow - don't compute shadows\n"+
//            //			"        -noreflec - don't do reflections\n"+
//            //			"        -notrans  - don't do transparency\n"+
//            "        -aa       - use anti-aliasing (~4x slower)\n"
//            + "        -multi    - use multi-threading (good for large, anti-aliased images)";
////			"        -nocap    - cylinders and cones are infinite";

    public static boolean DEBUG = false;
    public static boolean ANTI_ALIAS = false;
    public static boolean MULTI_THREAD = false;

    public void doIt() throws FileNotFoundException, IOException, InterruptedException {
        Path input = Paths.get(inputFile);
        Path output = Paths.get(outputFile);
        if (Files.exists(input)) {
            int cols = width;
            int rows = height;
            RayTracer rayTracer = new RayTracer(cols, rows);
            rayTracer.readScene(input.toFile());
            rayTracer.draw(output.toFile());
        } else {
            logger.log(Level.SEVERE, "Missing input scene file:{0}", inputFile);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Main m = new Main();
        new JCommander(m, args);
        m.doIt();
    }
}
