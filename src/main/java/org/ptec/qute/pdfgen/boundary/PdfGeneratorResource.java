package org.ptec.qute.pdfgen.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;

@Path("/pdf")
public class PdfGeneratorResource {


    Engine engine = Engine.builder().addDefaults().build();
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        String templ = "<div>Hello World {name}</div>";
        Template helloTemplate = engine.parse(templ);
        String result = helloTemplate.data("name", "Jim").render(); 
        return result;
    }
    
}
