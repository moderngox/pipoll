/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-04 at 22:04:20 UTC 
 * Modify at your own risk.
 */

package com.pipoll.entity.rssnodeendpoint;

/**
 * Service definition for Rssnodeendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RssnodeendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Rssnodeendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the rssnodeendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://nimble-lead-87107.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "rssnodeendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Rssnodeendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Rssnodeendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getRSSNode".
   *
   * This request holds the parameters needed by the rssnodeendpoint server.  After setting any
   * optional parameters, call the {@link GetRSSNode#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetRSSNode getRSSNode(java.lang.String id) throws java.io.IOException {
    GetRSSNode result = new GetRSSNode(id);
    initialize(result);
    return result;
  }

  public class GetRSSNode extends RssnodeendpointRequest<com.pipoll.entity.rssnodeendpoint.model.RSSNode> {

    private static final String REST_PATH = "rssnode/{id}";

    /**
     * Create a request for the method "getRSSNode".
     *
     * This request holds the parameters needed by the the rssnodeendpoint server.  After setting any
     * optional parameters, call the {@link GetRSSNode#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetRSSNode#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetRSSNode(java.lang.String id) {
      super(Rssnodeendpoint.this, "GET", REST_PATH, null, com.pipoll.entity.rssnodeendpoint.model.RSSNode.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetRSSNode setAlt(java.lang.String alt) {
      return (GetRSSNode) super.setAlt(alt);
    }

    @Override
    public GetRSSNode setFields(java.lang.String fields) {
      return (GetRSSNode) super.setFields(fields);
    }

    @Override
    public GetRSSNode setKey(java.lang.String key) {
      return (GetRSSNode) super.setKey(key);
    }

    @Override
    public GetRSSNode setOauthToken(java.lang.String oauthToken) {
      return (GetRSSNode) super.setOauthToken(oauthToken);
    }

    @Override
    public GetRSSNode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetRSSNode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetRSSNode setQuotaUser(java.lang.String quotaUser) {
      return (GetRSSNode) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetRSSNode setUserIp(java.lang.String userIp) {
      return (GetRSSNode) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String id;

    /**

     */
    public java.lang.String getId() {
      return id;
    }

    public GetRSSNode setId(java.lang.String id) {
      this.id = id;
      return this;
    }

    @Override
    public GetRSSNode set(String parameterName, Object value) {
      return (GetRSSNode) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertRSSNode".
   *
   * This request holds the parameters needed by the rssnodeendpoint server.  After setting any
   * optional parameters, call the {@link InsertRSSNode#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.pipoll.entity.rssnodeendpoint.model.RSSNode}
   * @return the request
   */
  public InsertRSSNode insertRSSNode(com.pipoll.entity.rssnodeendpoint.model.RSSNode content) throws java.io.IOException {
    InsertRSSNode result = new InsertRSSNode(content);
    initialize(result);
    return result;
  }

  public class InsertRSSNode extends RssnodeendpointRequest<com.pipoll.entity.rssnodeendpoint.model.RSSNode> {

    private static final String REST_PATH = "rssnode";

    /**
     * Create a request for the method "insertRSSNode".
     *
     * This request holds the parameters needed by the the rssnodeendpoint server.  After setting any
     * optional parameters, call the {@link InsertRSSNode#execute()} method to invoke the remote
     * operation. <p> {@link InsertRSSNode#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.pipoll.entity.rssnodeendpoint.model.RSSNode}
     * @since 1.13
     */
    protected InsertRSSNode(com.pipoll.entity.rssnodeendpoint.model.RSSNode content) {
      super(Rssnodeendpoint.this, "POST", REST_PATH, content, com.pipoll.entity.rssnodeendpoint.model.RSSNode.class);
    }

    @Override
    public InsertRSSNode setAlt(java.lang.String alt) {
      return (InsertRSSNode) super.setAlt(alt);
    }

    @Override
    public InsertRSSNode setFields(java.lang.String fields) {
      return (InsertRSSNode) super.setFields(fields);
    }

    @Override
    public InsertRSSNode setKey(java.lang.String key) {
      return (InsertRSSNode) super.setKey(key);
    }

    @Override
    public InsertRSSNode setOauthToken(java.lang.String oauthToken) {
      return (InsertRSSNode) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertRSSNode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertRSSNode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertRSSNode setQuotaUser(java.lang.String quotaUser) {
      return (InsertRSSNode) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertRSSNode setUserIp(java.lang.String userIp) {
      return (InsertRSSNode) super.setUserIp(userIp);
    }

    @Override
    public InsertRSSNode set(String parameterName, Object value) {
      return (InsertRSSNode) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listRSSNode".
   *
   * This request holds the parameters needed by the rssnodeendpoint server.  After setting any
   * optional parameters, call the {@link ListRSSNode#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListRSSNode listRSSNode() throws java.io.IOException {
    ListRSSNode result = new ListRSSNode();
    initialize(result);
    return result;
  }

  public class ListRSSNode extends RssnodeendpointRequest<com.pipoll.entity.rssnodeendpoint.model.CollectionResponseRSSNode> {

    private static final String REST_PATH = "rssnode";

    /**
     * Create a request for the method "listRSSNode".
     *
     * This request holds the parameters needed by the the rssnodeendpoint server.  After setting any
     * optional parameters, call the {@link ListRSSNode#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListRSSNode#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListRSSNode() {
      super(Rssnodeendpoint.this, "GET", REST_PATH, null, com.pipoll.entity.rssnodeendpoint.model.CollectionResponseRSSNode.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListRSSNode setAlt(java.lang.String alt) {
      return (ListRSSNode) super.setAlt(alt);
    }

    @Override
    public ListRSSNode setFields(java.lang.String fields) {
      return (ListRSSNode) super.setFields(fields);
    }

    @Override
    public ListRSSNode setKey(java.lang.String key) {
      return (ListRSSNode) super.setKey(key);
    }

    @Override
    public ListRSSNode setOauthToken(java.lang.String oauthToken) {
      return (ListRSSNode) super.setOauthToken(oauthToken);
    }

    @Override
    public ListRSSNode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListRSSNode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListRSSNode setQuotaUser(java.lang.String quotaUser) {
      return (ListRSSNode) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListRSSNode setUserIp(java.lang.String userIp) {
      return (ListRSSNode) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListRSSNode setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListRSSNode setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListRSSNode set(String parameterName, Object value) {
      return (ListRSSNode) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeRSSNode".
   *
   * This request holds the parameters needed by the rssnodeendpoint server.  After setting any
   * optional parameters, call the {@link RemoveRSSNode#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveRSSNode removeRSSNode(java.lang.String id) throws java.io.IOException {
    RemoveRSSNode result = new RemoveRSSNode(id);
    initialize(result);
    return result;
  }

  public class RemoveRSSNode extends RssnodeendpointRequest<Void> {

    private static final String REST_PATH = "rssnode/{id}";

    /**
     * Create a request for the method "removeRSSNode".
     *
     * This request holds the parameters needed by the the rssnodeendpoint server.  After setting any
     * optional parameters, call the {@link RemoveRSSNode#execute()} method to invoke the remote
     * operation. <p> {@link RemoveRSSNode#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveRSSNode(java.lang.String id) {
      super(Rssnodeendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveRSSNode setAlt(java.lang.String alt) {
      return (RemoveRSSNode) super.setAlt(alt);
    }

    @Override
    public RemoveRSSNode setFields(java.lang.String fields) {
      return (RemoveRSSNode) super.setFields(fields);
    }

    @Override
    public RemoveRSSNode setKey(java.lang.String key) {
      return (RemoveRSSNode) super.setKey(key);
    }

    @Override
    public RemoveRSSNode setOauthToken(java.lang.String oauthToken) {
      return (RemoveRSSNode) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveRSSNode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveRSSNode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveRSSNode setQuotaUser(java.lang.String quotaUser) {
      return (RemoveRSSNode) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveRSSNode setUserIp(java.lang.String userIp) {
      return (RemoveRSSNode) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String id;

    /**

     */
    public java.lang.String getId() {
      return id;
    }

    public RemoveRSSNode setId(java.lang.String id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveRSSNode set(String parameterName, Object value) {
      return (RemoveRSSNode) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateRSSNode".
   *
   * This request holds the parameters needed by the rssnodeendpoint server.  After setting any
   * optional parameters, call the {@link UpdateRSSNode#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.pipoll.entity.rssnodeendpoint.model.RSSNode}
   * @return the request
   */
  public UpdateRSSNode updateRSSNode(com.pipoll.entity.rssnodeendpoint.model.RSSNode content) throws java.io.IOException {
    UpdateRSSNode result = new UpdateRSSNode(content);
    initialize(result);
    return result;
  }

  public class UpdateRSSNode extends RssnodeendpointRequest<com.pipoll.entity.rssnodeendpoint.model.RSSNode> {

    private static final String REST_PATH = "rssnode";

    /**
     * Create a request for the method "updateRSSNode".
     *
     * This request holds the parameters needed by the the rssnodeendpoint server.  After setting any
     * optional parameters, call the {@link UpdateRSSNode#execute()} method to invoke the remote
     * operation. <p> {@link UpdateRSSNode#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.pipoll.entity.rssnodeendpoint.model.RSSNode}
     * @since 1.13
     */
    protected UpdateRSSNode(com.pipoll.entity.rssnodeendpoint.model.RSSNode content) {
      super(Rssnodeendpoint.this, "PUT", REST_PATH, content, com.pipoll.entity.rssnodeendpoint.model.RSSNode.class);
    }

    @Override
    public UpdateRSSNode setAlt(java.lang.String alt) {
      return (UpdateRSSNode) super.setAlt(alt);
    }

    @Override
    public UpdateRSSNode setFields(java.lang.String fields) {
      return (UpdateRSSNode) super.setFields(fields);
    }

    @Override
    public UpdateRSSNode setKey(java.lang.String key) {
      return (UpdateRSSNode) super.setKey(key);
    }

    @Override
    public UpdateRSSNode setOauthToken(java.lang.String oauthToken) {
      return (UpdateRSSNode) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateRSSNode setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateRSSNode) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateRSSNode setQuotaUser(java.lang.String quotaUser) {
      return (UpdateRSSNode) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateRSSNode setUserIp(java.lang.String userIp) {
      return (UpdateRSSNode) super.setUserIp(userIp);
    }

    @Override
    public UpdateRSSNode set(String parameterName, Object value) {
      return (UpdateRSSNode) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Rssnodeendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Rssnodeendpoint}. */
    @Override
    public Rssnodeendpoint build() {
      return new Rssnodeendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RssnodeendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRssnodeendpointRequestInitializer(
        RssnodeendpointRequestInitializer rssnodeendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(rssnodeendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
