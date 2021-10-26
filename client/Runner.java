package client;

import java.util.*;

import business.EntityAggregator;
import dao.DBConnection;
import dao.DBPool;
import data.*;

public class Runner {

    public static void main(String args[]) {
        // Fetch the Singletone DBPool instance, get a connection, and fetch Data
        DBPool singletonPool = DBPool.getInstance();
        DBConnection dbc = singletonPool.acquireConnection(1);
        List<String> dataLines = dbc.getData().getDataLines();

        // Initializing three entity aggregators, 2 for aggregating customers against
        // entities Geozone and Contract, and the third to aggregate Build duration
        // against Geozone
        EntityAggregator customerContractAggregator = new EntityAggregator();
        EntityAggregator customerGeoZoneAggregator = new EntityAggregator();
        EntityAggregator buildAggregator = new EntityAggregator();

        // To store a Map of values to finally display output from
        Map<Long, Entity> contractIDs = new HashMap<Long, Entity>();
        Map<Long, Entity> geozoneIDs = new HashMap<Long, Entity>();
        Map<Long, Long> avgBuilds = new HashMap<Long, Long>();

        try {
            for (String dl : dataLines) {
                /* Processing Data to Objects Starts */
                String splitData[] = dl.split(",");

                Customer customer = new Customer();
                customer.setId(Long.parseLong(splitData[0]));

                Contract contract = new Contract();
                contract.setId(Long.parseLong(splitData[1]));

                Geozone geozone = new Geozone();
                geozone.setId(splitData[2].hashCode());
                geozone.setZone(splitData[2]);

                Team team = new Team();
                team.setId(splitData[3].hashCode());

                Project project = new Project();
                project.setBuildDuration(Long.parseLong(splitData[5].substring(0, splitData[5].length() - 1)));
                project.setId(splitData[4].hashCode());

                contractIDs.put(contract.getId(), contract);
                geozoneIDs.put(geozone.getId(), geozone);
                /* Processing Data to Object Ends */

                // Aggregation calls which will in turn call the Entity Aggregator methods
                // acting on different implementations of Entity
                clubCustomersByContract(customerContractAggregator, customer, contract, geozone, team, project);
                clubCustomersByGeozone(customerGeoZoneAggregator, customer, contract, geozone, team, project);
                clubBuilds(buildAggregator, customer, contract, geozone, team, project, avgBuilds);
            }

            // Metrics from Aggregation 1 i.e. Geozone
            for (long gId : geozoneIDs.keySet()) {
                System.out.println("The average build duration for " + ((Geozone) (geozoneIDs.get(gId))).getZone()
                        + " is : " + avgBuilds.get(gId));
                System.out.println();

                System.out.println("The list of unique Customers under geozone : "
                        + ((Geozone) (geozoneIDs.get(gId))).getZone() + " :");
                // separate call to print the customers in a modular fashion
                printCustomers(customerGeoZoneAggregator, gId);
                System.out.println();

                System.out.print("The number of unique customers under geozone : "
                        + ((Geozone) (geozoneIDs.get(gId))).getZone() + " is :");
                System.out.println(customerGeoZoneAggregator.getUniqueCount(gId));
                System.out.println();

            }

            // Metrics from Aggregation 2 i.e. Contract
            for (long cntrcId : contractIDs.keySet()) {
                System.out.println("The number of unique customers under contract : " + contractIDs.get(cntrcId).getId()
                        + " is :" + customerContractAggregator.getUniqueCount(cntrcId));
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

    /**
     * Method to trigger the Entity Aggregator to initialize and aggregate the
     * customers against a Contract through the customerContractAggregator
     *
     * @param customerContractAggregator
     * @param customer
     * @param contract
     * @param geozone
     * @param team
     * @param project
     */
    public static void clubCustomersByContract(EntityAggregator customerContractAggregator, Customer customer,
            Contract contract, Geozone geozone, Team team, Project project) {

        customerContractAggregator.init(contract.getId(), customer);
    }

    /**
     * Method to trigger the Entity Aggregator to initialize and aggregate the
     * customers against a Geozone through the customerGeozoneAggregator
     *
     * @param customerGeozoneAggregator
     * @param customer
     * @param contract
     * @param geozone
     * @param team
     * @param project
     */
    public static void clubCustomersByGeozone(EntityAggregator customerGeozoneAggregator, Customer customer,
            Contract contract, Geozone geozone, Team team, Project project) {

        customerGeozoneAggregator.init(geozone.getId(), customer);
        customerGeozoneAggregator.getUniqueEntities(geozone.getId());
    }

    /**
     * Method to trigger the Entity Aggregator to initialize and average the builds
     * through buildAggregator
     *
     * @param buildAggregator
     * @param customer
     * @param contract
     * @param geozone
     * @param team
     * @param project
     * @param avgBuilds
     */
    public static void clubBuilds(EntityAggregator buildAggregator, Customer customer, Contract contract,
            Geozone geozone, Team team, Project project, Map<Long, Long> avgBuilds) {

        buildAggregator.init(geozone.getId(), project);
        long avgBuildDuration = buildAggregator.average(geozone.getId());

        avgBuilds.put(geozone.getId(), avgBuildDuration);
    }

    /**
     * print the customers in a modular fashion
     *
     * @param customerGeoZoneAggregator
     * @param gId
     */
    private static void printCustomers(EntityAggregator customerGeoZoneAggregator, long gId) {
        for (Entity customer : customerGeoZoneAggregator.getUniqueEntities(gId)) {
            System.out.println(customer.getId());
        }
    }

}
