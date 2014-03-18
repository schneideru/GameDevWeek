package de.hochschuletrier.gdw.ws1314.network.datagrams;

import de.hochschuletrier.gdw.commons.netcode.NetConnection;
import de.hochschuletrier.gdw.commons.netcode.datagram.INetDatagram;
import de.hochschuletrier.gdw.commons.netcode.message.INetMessageIn;
import de.hochschuletrier.gdw.commons.netcode.message.INetMessageOut;
import de.hochschuletrier.gdw.ws1314.network.DatagramHandler;

/**
 * Created by albsi on 17.03.14.
 */
public class EventDatagram extends BaseDatagram {
    public static final byte EVENT_DATAGRAM = INetDatagram.Type.FIRST_CUSTOM + 0x30;
    private long id;

    public EventDatagram (byte type, short id, short param1, short param2) {
        super (MessageType.NORMAL, type, id, param1, param2);
    }

    public EventDatagram (long id) {
        super (MessageType.NORMAL, EVENT_DATAGRAM, (short) 0, (short) 0, (short) 0);
        this.id = id;
    }

    @Override
    public void handle (DatagramHandler handler, NetConnection connection) {
        handler.handle (this, connection);
    }

    @Override
    public void writeToMessage (INetMessageOut message) {
        message.putLong (id);
    }

    @Override
    public void readFromMessage (INetMessageIn message) {
        id = message.getLong ();
    }

    public long getId () {
        return id;
    }
}