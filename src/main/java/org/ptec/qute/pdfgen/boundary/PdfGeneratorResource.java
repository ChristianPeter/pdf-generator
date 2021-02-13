package org.ptec.qute.pdfgen.boundary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import org.ptec.qute.pdfgen.entity.PdfGeneratorRequest;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;

@Path("/pdf")
public class PdfGeneratorResource {

    Engine engine = Engine.builder().addDefaults().build();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws IOException {

        String templ = "<div>Hello World {name} <img src='flyingsaucer.png'/></div>";
        Template helloTemplate = engine.parse(templ);
        
        System.out.println(PdfGeneratorResource.class.getResource("/").toExternalForm());
        String result = helloTemplate.data("name", "Jim").render(); 

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        //builder.withUri("file:///Users/me/Documents/pdf/in.htm");
        builder.withHtmlContent(result, PdfGeneratorResource.class.getResource("/images/").toExternalForm());
        builder.toStream(baos);
        builder.run();

        byte[] pdfdata = baos.toByteArray();

        byte[] encode = Base64.getEncoder().encode(pdfdata);
        
        return new String(encode, StandardCharsets.UTF_8);
    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String pdfb64(PdfGeneratorRequest payload) throws IOException {
        System.out.println(payload.template);
        System.out.println(payload.data);

        Template helloTemplate = engine.parse(payload.template);
        
        String result = helloTemplate.data(payload.data).render(); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        //builder.withUri("file:///Users/me/Documents/pdf/in.htm");
        builder.withHtmlContent(result, PdfGeneratorResource.class.getResource("/images/").toExternalForm());
        builder.toStream(baos);
        builder.run();

        byte[] pdfdata = baos.toByteArray();

        byte[] encode = Base64.getEncoder().encode(pdfdata);
        
        return new String(encode, StandardCharsets.UTF_8);

    }
    
}
