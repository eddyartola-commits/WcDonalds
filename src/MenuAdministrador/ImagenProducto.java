package MenuAdministrador;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
/** Acepta rutas del proyecto, rutas absolutas y recursos incluidos en el JAR. */
public final class ImagenProducto {
    private ImagenProducto(){}
    public static BufferedImage cargar(String ruta) {
        if(ruta==null||ruta.trim().isEmpty())return null;
        try {
            File f=new File(ruta);if(f.isFile())return ImageIO.read(f);
            String r=ruta.replace('\\','/');if(r.startsWith("src/"))r=r.substring(4);if(!r.startsWith("/"))r="/"+r;
            try(InputStream in=ImagenProducto.class.getResourceAsStream(r)){return in==null?null:ImageIO.read(in);}
        }catch(IOException|IllegalArgumentException e){return null;}
    }
}
