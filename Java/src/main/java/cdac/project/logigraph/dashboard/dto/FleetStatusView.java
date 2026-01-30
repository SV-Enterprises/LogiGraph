package cdac.project.logigraph.dashboard.dto;

import cdac.project.logigraph.vehicle.enums.VehicleStatus;

public class FleetStatusView {

    private final VehicleStatus status;
    private final long count;

    public FleetStatusView(VehicleStatus status, long count) {
        this.status = status;
        this.count = count;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public long getCount() {
        return count;
    }
}
