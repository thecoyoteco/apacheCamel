package com.example.camel.spring;

public class DistributeOrderXML {
    public static void main(String[] args) throws Exception {
        System.out.println("\n->> this is my spring test");
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "SpringRouteContext.xml");
        CamelContext camelContext = SpringCamelContext.springCamelContext(appContext, false);
        try {
            camelContext.start();
            ProducerTemplate orderProducerTemplate = camelContext.createProducerTemplate();
            InputStream orderInputStream = new FileInputStream(ClassLoader.getSystemClassLoader()
                    .getResource("order.xml")
                    .getFile());

            orderProducerTemplate.sendBody("direct:DistributeOrderXML", orderInputStream);

            int size = 0;
            switch (size) {
                case 0:
                    size = 10;
                    break;
            }

        } finally {
            camelContext.stop();
        }
    }
}
