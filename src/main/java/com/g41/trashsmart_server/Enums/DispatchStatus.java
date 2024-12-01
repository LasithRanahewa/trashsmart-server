package com.g41.trashsmart_server.Enums;

public enum DispatchStatus {
    NEW,
    DISPATCHED,
    COMPLETED,
    CANCELLED,
    PENDING // Added for the household dispatches. if no driver is available, the status will be pending until a driver is assigned
}
