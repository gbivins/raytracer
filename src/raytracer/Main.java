package raytracer;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    @Parameter(names = {"--infile", "-i"}, required = true)
    String inputFile;
    @Parameter(names = {"--outfile", "o"})
    String outputFile = "out.bmp";
    @Parameter(names = {"--width", "w"})
    int width = 1024;
    @Parameter(names = {"--height", "h"})
    int height = 1024;

    private static final String USAGE = "Usage:\n"
            + "java -cp src raytracer.Main infile bmpfile width height [-options]\n"
            + "\n"
            + "    where:\n"
            + "        infile    - input file name\n"
            + "        bmpfile   - bmp output file name\n"
            + "        width     - image width (in pixels)\n"
            + "        height    - image hreight (in pixels)\n"
            + //			"        -test     - run in test mode (see below)\n"+
            //			"        -noshadow - don't compute shadows\n"+
            //			"        -noreflec - don't do reflections\n"+
            //			"        -notrans  - don't do transparency\n"+
            "        -aa       - use anti-aliasing (~4x slower)\n"
            + "        -multi    - use multi-threading (good for large, anti-aliased images)";
//			"        -nocap    - cylinders and cones are infinite";

    public static boolean DEBUG = false;
    public static boolean ANTI_ALIAS = false;
    public static boolean MULTI_THREAD = false;

    private static void printUsage() {
        System.out.println(USAGE);
    }

    public void doIt() throws FileNotFoundException, IOException, InterruptedException {
        Path input = Paths.get(inputFile);
        Path output = Paths.get(outputFile);
        if (Files.exists(input)) {
            int cols = width;
            int rows = height;
            RayTracer rayTracer = new RayTracer(cols, rows);
            rayTracer.readScene(input.toFile());
            rayTracer.draw(output.toFile());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Main m = new Main();
        new JCommander(m, args);
        m.doIt();
//		if(args.length < 4) {
//			printUsage();
//			System.exit(0);
//		}

        // required arguments
//		File inFile = new File(args[0]);
//		File outFile = new File(args[1]);
//		int cols = Integer.parseInt(args[2]);
//		int rows = Integer.parseInt(args[3]);
        // optional arguments
//		int i = 0;
//		for(String arg: args) {
//			if(i++ < 4) continue;
//			if("-test".equals(arg)) {
//				DEBUG = true;
//			} else if("-aa".equals(arg)) {
//				ANTI_ALIAS = true;
//			} else if("-multi".equals(arg)) {
//				MULTI_THREAD = true;
//			} else {
//				System.out.print("Unrecognized option: '" + arg + "' ignored.");
//			}
//		}
//		RayTracer rayTracer = new RayTracer(cols, rows);
//		rayTracer.readScene(inFile);
//		if(DEBUG) {
//			while(true) {
//				Scanner scanner = new Scanner(System.in);
//				System.out.println("Input column and row of pixel (relative to upper left corner):");
//				int col = scanner.nextInt();
//				int row = scanner.nextInt();
//				rayTracer.getPixelColor(col, row);
//			}
//		} else {
//			rayTracer.draw(outFile);
//		}
    }
}
