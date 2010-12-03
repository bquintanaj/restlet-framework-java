/**
 * Copyright 2005-2010 Noelios Technologies.
 * 
 * The contents of this file are subject to the terms of one of the following
 * open source licenses: LGPL 3.0 or LGPL 2.1 or CDDL 1.0 or EPL 1.0 (the
 * "Licenses"). You can select the license that you prefer but you may not use
 * this file except in compliance with one of these Licenses.
 * 
 * You can obtain a copy of the LGPL 3.0 license at
 * http://www.opensource.org/licenses/lgpl-3.0.html
 * 
 * You can obtain a copy of the LGPL 2.1 license at
 * http://www.opensource.org/licenses/lgpl-2.1.php
 * 
 * You can obtain a copy of the CDDL 1.0 license at
 * http://www.opensource.org/licenses/cddl1.php
 * 
 * You can obtain a copy of the EPL 1.0 license at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * 
 * See the Licenses for the specific language governing permissions and
 * limitations under the Licenses.
 * 
 * Alternatively, you can obtain a royalty free commercial license with less
 * limitations, transferable or non-transferable, directly at
 * http://www.noelios.com/products/restlet-engine
 * 
 * Restlet is a registered trademark of Noelios Technologies.
 */

package org.restlet.ext.sip;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Tag;
import org.restlet.representation.Representation;

/**
 * Request part of a SIP transaction.
 * 
 * @author Jerome Louvel
 */
public class SipRequest extends Request {

    /** Alternative ring tone for the UAS. */
    private volatile Address alertInfo;

    /** The list of supported event packages. */
    private volatile List<EventType> allowedEventTypes;

    /** The description of the current caller. */
    private volatile List<Address> callerInfo;

    /**
     * Identifies a particular invitation or all registrations of a particular
     * client.
     */
    private volatile String callId;

    /** The identifier of the transaction. */
    private volatile String callSequence;

    /** The data about the contact. */
    private volatile List<ContactInfo> contact;

    /** The description of an event notification. */
    private volatile Event event;

    /** The description of the request's initiator. */
    private volatile Address from;

    /** The list of references to call-ids. */
    private volatile List<String> inReplyTo;

    /** The version of the MIME protocol used to construct the message. */
    private volatile String mimeVersion;

    /**
     * The name of the organization to which the SIP element issuing the message
     * belongs.
     */
    private volatile String organization;

    /** The urgency of the request as perceived by the client. */
    private volatile Priority priority;

    /** The proxy-sensitive features that the proxy must support. */
    private volatile List<OptionTag> proxyRequires;

    /** The intermediary recipients information. */
    private volatile List<SipRecipientInfo> recipientsInfo;

    /**
     * The list of routes completed by proxies to force future requests to go
     * through the proxy.
     */
    private volatile List<Address> recordedRoutes;

    /**
     * The reference that the recipient of a {@link SipMethod#REFER} method
     * should contact.
     */
    private volatile Address referTo;

    /**
     * A logical return URI that may be different from the
     * {@link SipRequest#from} attribute.
     */
    private volatile Address replyTo;

    /** The sensitive features that the server must support. */
    private volatile List<OptionTag> requires;

    /** The set of proxies used to force routing for a request. */
    private volatile List<Address> routes;

    /** Identifies the specific event state that the request is refreshing. */
    private volatile Tag sipIfMatch;

    /** The subject of the call. */
    private volatile String subject;

    /** The state of the subscription. */
    private volatile Subscription subscription;

    /** The extensions supported by the UAC. */
    private volatile List<OptionTag> supported;

    /** The logical recipient of the request. */
    private volatile Address to;

    /**
     * Constructor.
     */
    public SipRequest() {
        super();
    }

    /**
     * Constructor.
     * 
     * @param method
     *            The call's method.
     * @param resourceRef
     *            The resource reference.
     */
    public SipRequest(Method method, Reference resourceRef) {
        super(method, resourceRef);
    }

    /**
     * Constructor.
     * 
     * @param method
     *            The call's method.
     * @param resourceRef
     *            The resource reference.
     * @param entity
     *            The entity.
     */
    public SipRequest(Method method, Reference resourceRef,
            Representation entity) {
        super(method, resourceRef, entity);
    }

    /**
     * Constructor.
     * 
     * @param method
     *            The call's method.
     * @param resourceUri
     *            The resource URI.
     */
    public SipRequest(Method method, String resourceUri) {
        super(method, resourceUri);
    }

    /**
     * Constructor.
     * 
     * @param method
     *            The call's method.
     * @param resourceUri
     *            The resource URI.
     * @param entity
     *            The entity.
     */
    public SipRequest(Method method, String resourceUri, Representation entity) {
        super(method, resourceUri, entity);
    }

    /**
     * Copy constructor.
     * 
     * @param request
     *            The request to copy.
     */
    public SipRequest(SipRequest request) {
        super(request);
        this.alertInfo = request.getAlertInfo();
        this.allowedEventTypes = request.getAllowedEventTypes();
        this.callerInfo = request.getCallerInfo();
        this.callId = request.getCallId();
        this.callSequence = request.getCallSequence();
        this.contact = request.getContact();
        this.event = request.getEvent();
        this.from = request.getFrom();
        this.inReplyTo = request.getInReplyTo();
        this.mimeVersion = request.getMimeVersion();
        this.organization = request.getOrganization();
        this.priority = request.getPriority();
        this.proxyRequires = request.getProxyRequires();
        this.recipientsInfo = request.getSipRecipientsInfo();
        this.recordedRoutes = request.getRecordedRoutes();
        this.referTo = request.getReferTo();
        this.replyTo = request.getReplyTo();
        this.requires = request.getRequires();
        this.routes = request.getRoutes();
        this.sipIfMatch = request.getSipIfMatch();
        this.subject = request.getSubject();
        this.subscription = request.getSubscriptionState();
        this.supported = request.getSupported();
        this.to = request.getTo();
    }

    /**
     * Returns the alternative ring tone for the UAS.
     * 
     * @return The alternative ring tone for the UAS.
     */
    public Address getAlertInfo() {
        return alertInfo;
    }

    /**
     * Returns the list of supported event packages.
     * 
     * @return The list of supported event packages.
     */
    public List<EventType> getAllowedEventTypes() {
        // Lazy initialization with double-check.
        List<EventType> aet = this.allowedEventTypes;
        if (aet == null) {
            synchronized (this) {
                aet = this.allowedEventTypes;
                if (aet == null) {
                    this.allowedEventTypes = aet = new CopyOnWriteArrayList<EventType>();
                }
            }
        }

        return aet;
    }

    /**
     * Returns the description of the current caller.
     * 
     * @return The description of the current caller.
     */
    public List<Address> getCallerInfo() {
        // Lazy initialization with double-check.
        List<Address> ci = this.callerInfo;
        if (ci == null) {
            synchronized (this) {
                ci = this.callerInfo;
                if (ci == null) {
                    this.callerInfo = ci = new CopyOnWriteArrayList<Address>();
                }
            }
        }

        return ci;
    }

    /**
     * Returns the identifier of the call.
     * 
     * @return The identifier of the call.
     */
    public String getCallId() {
        return callId;
    }

    /**
     * Returns the identifier of a transaction.
     * 
     * @return The identifier of the transaction.
     */
    public String getCallSequence() {
        return callSequence;
    }

    /**
     * Returns the data about the contact.
     * 
     * @return The data about the contact.
     */
    public List<ContactInfo> getContact() {
        // Lazy initialization with double-check.
        List<ContactInfo> c = this.contact;
        if (c == null) {
            synchronized (this) {
                c = this.contact;
                if (c == null) {
                    this.contact = c = new CopyOnWriteArrayList<ContactInfo>();
                }
            }
        }

        return c;
    }

    /**
     * Returns the description of an event notification.
     * 
     * @return The description of an event notification.
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Returns the description of the request's initiator.
     * 
     * @return The description of the request's initiator.
     */
    public Address getFrom() {
        return from;
    }

    /**
     * Returns The list of references to call-ids.
     * 
     * @return The list of references to call-ids.
     */
    public List<String> getInReplyTo() {
        // Lazy initialization with double-check.
        List<String> irt = this.inReplyTo;
        if (irt == null) {
            synchronized (this) {
                irt = this.inReplyTo;
                if (irt == null) {
                    this.inReplyTo = irt = new CopyOnWriteArrayList<String>();
                }
            }
        }

        return irt;
    }

    /**
     * Returns the version of the MIME protocol used to construct the message.
     * 
     * @return The version of the MIME protocol used to construct the message.
     */
    public String getMimeVersion() {
        return mimeVersion;
    }

    /**
     * Returns the name of the organization to which the SIP element issuing the
     * message belongs.
     * 
     * @return The name of the organization to which the SIP element issuing the
     *         message belongs.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Returns the urgency of the request as perceived by the client.
     * 
     * @return The urgency of the request as perceived by the client.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Returns the proxy-sensitive features that the proxy must support.
     * 
     * @return The proxy-sensitive features that the proxy must support.
     */
    public List<OptionTag> getProxyRequires() {
        // Lazy initialization with double-check.
        List<OptionTag> pr = this.proxyRequires;
        if (pr == null) {
            synchronized (this) {
                pr = this.proxyRequires;
                if (pr == null) {
                    this.proxyRequires = pr = new CopyOnWriteArrayList<OptionTag>();
                }
            }
        }

        return pr;
    }

    /**
     * Returns the list of routes completed by proxies to force future requests
     * to go through the proxy.
     * 
     * @return The list of routes completed by proxies to force future requests
     *         to go through the proxy.
     */
    public List<Address> getRecordedRoutes() {
        // Lazy initialization with double-check.
        List<Address> rr = this.recordedRoutes;
        if (rr == null) {
            synchronized (this) {
                rr = this.recordedRoutes;
                if (rr == null) {
                    this.recordedRoutes = rr = new CopyOnWriteArrayList<Address>();
                }
            }
        }

        return rr;
    }

    /**
     * Returns the reference that the recipient of a {@link SipMethod#REFER}
     * method should contact.
     * 
     * @return The reference that the recipient of a {@link SipMethod#REFER}
     *         method should contact.
     */
    public Address getReferTo() {
        return referTo;
    }

    /**
     * Returns a logical return URI.
     * 
     * @return A logical return URI.
     */
    public Address getReplyTo() {
        return replyTo;
    }

    /**
     * Returns the sensitive features that the server must support.
     * 
     * @return The sensitive features that the server must support.
     */
    public List<OptionTag> getRequires() {
        // Lazy initialization with double-check.
        List<OptionTag> r = this.requires;
        if (r == null) {
            synchronized (this) {
                r = this.requires;
                if (r == null) {
                    this.requires = r = new CopyOnWriteArrayList<OptionTag>();
                }
            }
        }

        return r;
    }

    /**
     * Returns the set of proxies used to force routing for a request.
     * 
     * @return The set of proxies used to force routing for a request.
     */
    public List<Address> getRoutes() {
        // Lazy initialization with double-check.
        List<Address> r = this.routes;
        if (r == null) {
            synchronized (this) {
                r = this.routes;
                if (r == null) {
                    this.routes = r = new CopyOnWriteArrayList<Address>();
                }
            }
        }

        return r;
    }

    /**
     * Returns the identifier of the specific event state that the request is
     * refreshing.
     * 
     * @return The identifier of the specific event state that the request is
     *         refreshing.
     */
    public Tag getSipIfMatch() {
        return sipIfMatch;
    }

    /**
     * Returns the intermediary recipients information.
     * 
     * @return The intermediary recipients information.
     */
    public List<SipRecipientInfo> getSipRecipientsInfo() {
        // Lazy initialization with double-check.
        List<SipRecipientInfo> sri = this.recipientsInfo;
        if (sri == null) {
            synchronized (this) {
                sri = this.recipientsInfo;
                if (sri == null) {
                    this.recipientsInfo = sri = new CopyOnWriteArrayList<SipRecipientInfo>();
                }
            }
        }

        return sri;
    }

    /**
     * Returns the subject of the call.
     * 
     * @return The subject of the call.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns the state of the subscription.
     * 
     * @return The state of the subscription.
     */
    public Subscription getSubscriptionState() {
        return subscription;
    }

    /**
     * Returns the extensions supported by the UAC.
     * 
     * @return The extensions supported by the UAC.
     */
    public List<OptionTag> getSupported() {
        // Lazy initialization with double-check.
        List<OptionTag> s = this.supported;
        if (s == null) {
            synchronized (this) {
                s = this.supported;
                if (s == null) {
                    this.supported = s = new CopyOnWriteArrayList<OptionTag>();
                }
            }
        }

        return s;
    }

    /**
     * Returns the logical recipient of the request.
     * 
     * @return The logical recipient of the request.
     */
    public Address getTo() {
        return to;
    }

    /**
     * Sets the alternative ring tone for the UAS.
     * 
     * @param alertInfo
     *            The alternative ring tone for the UAS.
     */
    public void setAlertInfo(Address alertInfo) {
        this.alertInfo = alertInfo;
    }

    /**
     * Sets the list of supported event packages.
     * 
     * @param allowedEventTypes
     *            The list of supported event packages.
     */
    public void setAllowedEventTypes(List<EventType> allowedEventTypes) {
        this.allowedEventTypes = allowedEventTypes;
    }

    /**
     * Sets the description of the current caller.
     * 
     * @param callerInfo
     *            The description of the current caller.
     */
    public void setCallerInfo(List<Address> callerInfo) {
        this.callerInfo = callerInfo;
    }

    /**
     * Sets the identifier of the call.
     * 
     * @param callId
     *            The identifier of the call.
     */
    public void setCallId(String callId) {
        this.callId = callId;
    }

    /**
     * Sets the identifier of the transaction.
     * 
     * @param callSequence
     *            The identifier of the transaction.
     */
    public void setCallSequence(String callSequence) {
        this.callSequence = callSequence;
    }

    /**
     * Sets the data about the contact.
     * 
     * @param contact
     *            The data about the contact.
     */
    public void setContact(List<ContactInfo> contact) {
        this.contact = contact;
    }

    /**
     * Sets the description of an event notification.
     * 
     * @param event
     *            The description of an event notification.
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Sets the description of the request's initiator.
     * 
     * @param from
     *            The description of the request's initiator.
     */
    public void setFrom(Address from) {
        this.from = from;
    }

    /**
     * Sets the list of references to call-ids.
     * 
     * @param inReplyTo
     *            The list of references to call-ids.
     */
    public void setInReplyTo(List<String> inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    /**
     * Sets the version of the MIME protocol used to construct the message.
     * 
     * @param mimeVersion
     *            The version of the MIME protocol used to construct the
     *            message.
     */
    public void setMimeVersion(String mimeVersion) {
        this.mimeVersion = mimeVersion;
    }

    /**
     * Sets the name of the organization to which the SIP element issuing the
     * message belongs.
     * 
     * @param organization
     *            The name of the organization to which the SIP element issuing
     *            the message belongs.
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * Sets the urgency of the request as perceived by the client.
     * 
     * @param priority
     *            The urgency of the request as perceived by the client.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the proxy-sensitive features that the proxy must support.
     * 
     * @param proxyRequires
     *            The proxy-sensitive features that the proxy must support.
     */
    public void setProxyRequires(List<OptionTag> proxyRequires) {
        this.proxyRequires = proxyRequires;
    }

    /**
     * Sets the list of routes completed by proxies to force future requests to
     * go through the proxy.
     * 
     * @param recordedRoutes
     *            The list of routes completed by proxies to force future
     *            requests to go through the proxy.
     */
    public void setRecordedRoutes(List<Address> recordedRoutes) {
        this.recordedRoutes = recordedRoutes;
    }

    /**
     * Sets the reference that the recipient of a {@link SipMethod#REFER} method
     * should contact.
     * 
     * @param referTo
     *            The reference that the recipient of a {@link SipMethod#REFER}
     *            method should contact.
     */
    public void setReferTo(Address referTo) {
        this.referTo = referTo;
    }

    /**
     * Sets a logical return URI.
     * 
     * @param replyTo
     *            A logical return URI.
     */
    public void setReplyTo(Address replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * Sets the sensitive features that the server must support.
     * 
     * @param requires
     *            The sensitive features that the server must support.
     */
    public void setRequires(List<OptionTag> requires) {
        this.requires = requires;
    }

    /**
     * Sets the set of proxies used to force routing for a request.
     * 
     * @param routes
     *            The set of proxies used to force routing for a request.
     */
    public void setRoutes(List<Address> routes) {
        this.routes = routes;
    }

    /**
     * Sets the identifier of the specific event state that the request is
     * refreshing.
     * 
     * @param sipIfMatch
     *            The identifier of the specific event state that the request is
     *            refreshing.
     */
    public void setSipIfMatch(Tag sipIfMatch) {
        this.sipIfMatch = sipIfMatch;
    }

    /**
     * Sets the intermediary recipients information.
     * 
     * @param recipientsInfo
     *            The intermediary recipients information.
     */
    public void setSipRecipientsInfo(List<SipRecipientInfo> recipientsInfo) {
        this.recipientsInfo = recipientsInfo;
    }

    /**
     * Sets the subject of the call.
     * 
     * @param subject
     *            the subject of the call.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sets the state of the subscription.
     * 
     * @param subscription
     *            The state of the subscription.
     */
    public void setSubscriptionState(Subscription subscription) {
        this.subscription = subscription;
    }

    /**
     * Sets the extensions supported by the UAC.
     * 
     * @param supported
     *            The extensions supported by the UAC.
     */
    public void setSupported(List<OptionTag> supported) {
        this.supported = supported;
    }

    /**
     * Sets the logical recipient of the request.
     * 
     * @param to
     *            The logical recipient of the request.
     */
    public void setTo(Address to) {
        this.to = to;
    }

}