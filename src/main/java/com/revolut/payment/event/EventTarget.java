package com.revolut.payment.event;

import java.lang.reflect.Method;
import java.util.*;

public interface EventTarget<E extends Event> {

    public Map<Class, List<Object>> listeners = new HashMap<Class, List<Object>>();

    /**
     * @param e
     */
    default public void dispatchEvent( E e ) {

        listeners.get( e.getClass() ).stream().forEach( o -> {

            try {

                Method m = o.getClass().getMethod( "handle" + e.getClass().getSimpleName(), e.getClass() );
                m.invoke( o, e );

            } catch ( Exception ex ) {
                // fail silently
                ex.printStackTrace();
            }

        } );

    }

    /**
     * @param E
     * @param listener
     */
    default public void addEventListener( Class E, Object listener ) {

        if ( listeners.get( E ) == null ) {
            listeners.put( E, new ArrayList<>() );
        }

        listeners.get( E ).add( listener );

    }

}