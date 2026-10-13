package MenuAdministrador;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;

/** Catálogo A4 con la plantilla suministrada y seis productos por página. */
public final class CatalogoPDF {
    private static final int W=1654,H=2339;
    private static final Color ROJO=new Color(160,18,18),AMARILLO=new Color(255,188,13),TEXTO=new Color(28,28,28);
    private CatalogoPDF(){}
    public static void guardar(File destino,String grupo,List<Object[]> productos)throws IOException {
        BufferedImage plantilla=ImagenProducto.cargar("/Recursos/catalogo-template.png");
        if(plantilla==null)throw new IOException("Falta src/Recursos/catalogo-template.png. Copia la carpeta Recursos del paquete.");
        int paginas=Math.max(1,(productos.size()+5)/6);
        // Escribe cada página directamente; no acumula imágenes de todo el catálogo en memoria.
        Path ruta=destino.toPath().toAbsolutePath(),temp=Files.createTempFile(ruta.getParent(),"catalogo-",".tmp");
        try {
            try(Contador out=new Contador(new BufferedOutputStream(Files.newOutputStream(temp)))) {
                List<Long> offsets=new ArrayList<>();out.write(ascii("%PDF-1.4\n"));
                objeto(out,offsets,1,ascii("<< /Type /Catalog /Pages 2 0 R >>"));
                StringBuilder kids=new StringBuilder();for(int i=0;i<paginas;i++)kids.append(3+i*3).append(" 0 R ");
                objeto(out,offsets,2,ascii("<< /Type /Pages /Kids ["+kids+"] /Count "+paginas+" >>"));
                String fecha=ZonedDateTime.now(ZoneId.of("America/Guatemala")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                for(int i=0;i<paginas;i++) {
                    BufferedImage page=pagina(plantilla,grupo,productos,i,paginas,fecha);byte[] jpeg=jpeg(page);page.flush();int id=3+i*3;
                    objeto(out,offsets,id,ascii("<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595.276 841.89] /Resources << /XObject << /Im "+(id+2)+" 0 R >> >> /Contents "+(id+1)+" 0 R >>"));
                    byte[] contenido=ascii("q 595.276 0 0 841.89 0 0 cm /Im Do Q");
                    stream(out,offsets,id+1,"",contenido);
                    stream(out,offsets,id+2,"/Type /XObject /Subtype /Image /Width "+W+" /Height "+H+" /ColorSpace /DeviceRGB /BitsPerComponent 8 /Filter /DCTDecode ",jpeg);
                }
                long xref=out.cuenta;out.write(ascii("xref\n0 "+(offsets.size()+1)+"\n0000000000 65535 f \n"));for(long o:offsets)out.write(ascii(String.format(Locale.ROOT,"%010d 00000 n \n",o)));
                out.write(ascii("trailer\n<< /Size "+(offsets.size()+1)+" /Root 1 0 R >>\nstartxref\n"+xref+"\n%%EOF\n"));
            }
            Files.move(temp,ruta,StandardCopyOption.REPLACE_EXISTING);
        }finally{Files.deleteIfExists(temp);}
    }
    private static BufferedImage pagina(BufferedImage fondo,String grupo,List<Object[]> productos,int page,int total,String fecha) {
        BufferedImage b=new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);Graphics2D g=b.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(fondo,0,0,W,H,null);g.scale(W/1240.0,H/1754.0);
        g.setColor(ROJO);font(g,Font.BOLD,36);centrar(g,"CATÁLOGO DE PRODUCTOS",760,295);
        font(g,Font.BOLD,30);g.setColor(TEXTO);centrarAjustado(g,grupo,760,346,760);
        g.setColor(AMARILLO);g.fillRoundRect(650,365,220,6,6,6);
        font(g,Font.PLAIN,16);g.setColor(new Color(85,85,85));centrar(g,"Generado: "+fecha+"  |  "+productos.size()+" productos",760,404);
        for(int n=page*6;n<Math.min(productos.size(),page*6+6);n++) {
            Object[] p=productos.get(n);int local=n-page*6,x=205+(local%2)*484,y=445+(local/2)*355;int ancho=460,alto=330;
            g.setColor(new Color(238,238,238));g.fillRoundRect(x+2,y+3,ancho,alto,18,18);g.setColor(Color.WHITE);g.fillRoundRect(x,y,ancho,alto,18,18);g.setColor(new Color(211,211,211));g.setStroke(new BasicStroke(1));g.drawRoundRect(x,y,ancho,alto,18,18);
            BufferedImage imagen= p.length>5 ? ImagenProducto.cargar((String)p[5]):null;
            if(imagen!=null){double f=Math.min(410.0/imagen.getWidth(),145.0/imagen.getHeight());int iw=(int)(imagen.getWidth()*f),ih=(int)(imagen.getHeight()*f);g.drawImage(imagen,x+(ancho-iw)/2,y+14+(145-ih)/2,iw,ih,null);}
            else{g.setColor(new Color(246,246,246));g.fillRoundRect(x+25,y+14,410,145,12,12);g.setColor(new Color(120,120,120));font(g,Font.PLAIN,18);centrar(g,"Sin imagen",x+ancho/2,y+96);}
            font(g,Font.BOLD,22);g.setColor(TEXTO);List<String> nombres=envolver(g,String.valueOf(p[1]),410,2);for(int j=0;j<nombres.size();j++)centrar(g,nombres.get(j),x+ancho/2,y+188+j*25);
            font(g,Font.PLAIN,15);g.setColor(new Color(90,90,90));String descripcion=p.length>6&&p[6]!=null?String.valueOf(p[6]):"";List<String> desc=envolver(g,descripcion,410,2);for(int j=0;j<desc.size();j++)centrar(g,desc.get(j),x+ancho/2,y+235+j*18);
            font(g,Font.BOLD,29);g.setColor(ROJO);centrar(g,"Q "+String.format(Locale.ROOT,"%.2f",p[2]),x+ancho/2,y+295);
            boolean activo=Boolean.TRUE.equals(p[3]);g.setColor(activo?AMARILLO:new Color(235,235,235));g.fillRoundRect(x+150,y+304,160,20,20,20);font(g,Font.BOLD,12);g.setColor(TEXTO);centrar(g,activo?"Disponible":"No disponible",x+ancho/2,y+318);
        }
        if(productos.isEmpty()){font(g,Font.PLAIN,23);g.setColor(TEXTO);centrar(g,"Esta categoría no tiene productos.",760,665);}
        g.setColor(ROJO);g.fillRect(250,1580,660,2);font(g,Font.PLAIN,15);g.setColor(new Color(85,85,85));centrar(g,"WcDonalds · "+grupo,590,1613);centrar(g,"Página "+(page+1)+" de "+total,590,1640);g.dispose();return b;
    }
    private static void font(Graphics2D g,int tipo,int size){g.setFont(new Font("SansSerif",tipo,size));}
    private static void centrar(Graphics2D g,String s,int centro,int y){g.drawString(s,centro-g.getFontMetrics().stringWidth(s)/2,y);}
    private static void centrarAjustado(Graphics2D g,String s,int centro,int y,int max){int tam=g.getFont().getSize();while(tam>15&&g.getFontMetrics().stringWidth(s)>max)g.setFont(g.getFont().deriveFont((float)--tam));centrar(g,s,centro,y);}
    private static List<String> envolver(Graphics2D g,String texto,int ancho,int max) {
        List<String> r=new ArrayList<>();String resto=texto.replaceAll("\\s+"," ").trim();
        while(!resto.isEmpty()&&r.size()<max){int fin=resto.length();while(fin>1&&g.getFontMetrics().stringWidth(resto.substring(0,fin))>ancho)fin--;if(fin<resto.length()){int espacio=resto.lastIndexOf(' ',fin);if(espacio>0)fin=espacio;}
            String linea=resto.substring(0,fin).trim();resto=resto.substring(fin).trim();if(r.size()==max-1&&!resto.isEmpty()){while(linea.length()>1&&g.getFontMetrics().stringWidth(linea+"...")>ancho)linea=linea.substring(0,linea.length()-1);linea+="...";}r.add(linea);}
        return r;
    }
    private static byte[] jpeg(BufferedImage image)throws IOException {ByteArrayOutputStream b=new ByteArrayOutputStream();ImageWriter writer=ImageIO.getImageWritersByFormatName("jpeg").next();try(ImageOutputStream out=ImageIO.createImageOutputStream(b)){writer.setOutput(out);ImageWriteParam p=writer.getDefaultWriteParam();p.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);p.setCompressionQuality(.93f);writer.write(null,new IIOImage(image,null,null),p);}finally{writer.dispose();}return b.toByteArray();}
    private static byte[] ascii(String s){return s.getBytes(StandardCharsets.US_ASCII);}
    private static void objeto(Contador out,List<Long> offsets,int id,byte[] data)throws IOException{offsets.add(out.cuenta);out.write(ascii(id+" 0 obj\n"));out.write(data);out.write(ascii("\nendobj\n"));}
    private static void stream(Contador out,List<Long> offsets,int id,String atributos,byte[] data)throws IOException{offsets.add(out.cuenta);out.write(ascii(id+" 0 obj\n<< "+atributos+"/Length "+data.length+" >>\nstream\n"));out.write(data);out.write(ascii("\nendstream\nendobj\n"));}
    private static class Contador extends FilterOutputStream {long cuenta;Contador(OutputStream o){super(o);}public void write(int b)throws IOException{out.write(b);cuenta++;}public void write(byte[] b,int off,int len)throws IOException{out.write(b,off,len);cuenta+=len;}public void write(byte[] b)throws IOException{write(b,0,b.length);}}
}
