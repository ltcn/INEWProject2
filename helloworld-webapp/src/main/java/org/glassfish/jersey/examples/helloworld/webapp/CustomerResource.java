package org.glassfish.jersey.examples.helloworld.webapp;

import java.util.List;
import javax.swing.JOptionPane;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author dawit.gebremichael1
 */
@Path("customer")
public class CustomerResource {

    @GET
    @Produces("text/plain")
    public String getHello() {
        return "Hello Customer!";
    }

    @GET
    @Path("/{model : .+}/year/{year}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPicture(@PathParam("make") String make,
            @PathParam("model") List<PathSegment> car,
            @PathParam("year") String year) {
        return "Got it "+car.toString() + year;
    }

    /***
     * /customers?start=0&size=10
     * @param start
     * @param size
     * @return 
     */
    @Path("/customers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCustomers(@QueryParam("start") int start,
            @QueryParam("size") int size) {
        CustomerManager cm = new CustomerManager();
        //JOptionPane.showMessageDialog(null, cm.getCustomers());
        String customerList = cm.getCustomers().toString();
        return "start = "+start+" size = "+size+" "+customerList;
    }
    
    public static void main(String[] args) {
         CustomerManager cm = new CustomerManager();
        JOptionPane.showMessageDialog(null, cm.getCustomers());
    }

}
