package xyz.kiradev.vitality.api.util.redis.interfaces;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/6/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

public interface Packet {
    void onReceived();
    default void onSend(){}
}
