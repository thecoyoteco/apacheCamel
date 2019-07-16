package com.example.camel.java;

public class DistributeOrderDSL {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        System.out.println("-> Started Camel context");
        try {
            context.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:camel.example.java.DistributeOrderDSL")
                            .split(xpath("//order[@product='soaps']/items"))
                            .log("-> logging to console")
                            .to("stream:out");
                    //.to("file:src/main/resources/order/");
                }
            });
            context.start();
            ProducerTemplate orderProducerTemplate = context.createProducerTemplate();
            InputStream orderInputStream = new FileInputStream(ClassLoader.getSystemClassLoader()
                    .getResource("order.xml").getFile());
            orderProducerTemplate.sendBody("direct:camel.example.java.DistributeOrderDSL", orderInputStream);
        } finally {
            context.stop();
        }
    }
}
