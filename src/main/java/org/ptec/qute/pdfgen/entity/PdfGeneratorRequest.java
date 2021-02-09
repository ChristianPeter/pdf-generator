package org.ptec.qute.pdfgen.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.vertx.core.json.JsonObject;

public class PdfGeneratorRequest {
    public String template;
    public Map<String, Object> data;
    public List<PdfGeneratorRequestImage> images = new ArrayList<>();
    
    public static class PdfGeneratorRequestImage {
        public String id;
        public String data;
    }

}