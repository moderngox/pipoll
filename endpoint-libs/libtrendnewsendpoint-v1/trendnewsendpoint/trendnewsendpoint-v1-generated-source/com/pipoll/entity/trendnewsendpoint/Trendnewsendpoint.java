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
 * on 2015-03-14 at 00:35:01 UTC 
 * Modify at your own risk.
 */

package com.pipoll.entity.trendnewsendpoint;

/**
 * Service definition for Trendnewsendpoint (v1).
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
 * This service uses {@link TrendnewsendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Trendnewsendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the trendnewsendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "trendnewsendpoint/v1/";

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
  public Trendnewsendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Trendnewsendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getTrendNews".
   *
   * This request holds the parameters needed by the trendnewsendpoint server.  After setting any
   * optional parameters, call the {@link GetTrendNews#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetTrendNews getTrendNews(java.lang.String id) throws java.io.IOException {
    GetTrendNews result = new GetTrendNews(id);
    initialize(result);
    return result;
  }

  public class GetTrendNews extends TrendnewsendpointRequest<com.pipoll.entity.trendnewsendpoint.model.TrendNews> {

    private static final String REST_PATH = "trendnews/{id}";

    /**
     * Create a request for the method "getTrendNews".
     *
     * This request holds the parameters needed by the the trendnewsendpoint server.  After setting
     * any optional parameters, call the {@link GetTrendNews#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetTrendNews#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetTrendNews(java.lang.String id) {
      super(Trendnewsendpoint.this, "GET", REST_PATH, null, com.pipoll.entity.trendnewsendpoint.model.TrendNews.class);
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
    public GetTrendNews setAlt(java.lang.String alt) {
      return (GetTrendNews) super.setAlt(alt);
    }

    @Override
    public GetTrendNews setFields(java.lang.String fields) {
      return (GetTrendNews) super.setFields(fields);
    }

    @Override
    public GetTrendNews setKey(java.lang.String key) {
      return (GetTrendNews) super.setKey(key);
    }

    @Override
    public GetTrendNews setOauthToken(java.lang.String oauthToken) {
      return (GetTrendNews) super.setOauthToken(oauthToken);
    }

    @Override
    public GetTrendNews setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetTrendNews) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetTrendNews setQuotaUser(java.lang.String quotaUser) {
      return (GetTrendNews) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetTrendNews setUserIp(java.lang.String userIp) {
      return (GetTrendNews) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String id;

    /**

     */
    public java.lang.String getId() {
      return id;
    }

    public GetTrendNews setId(java.lang.String id) {
      this.id = id;
      return this;
    }

    @Override
    public GetTrendNews set(String parameterName, Object value) {
      return (GetTrendNews) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertTrendNews".
   *
   * This request holds the parameters needed by the trendnewsendpoint server.  After setting any
   * optional parameters, call the {@link InsertTrendNews#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.pipoll.entity.trendnewsendpoint.model.TrendNews}
   * @return the request
   */
  public InsertTrendNews insertTrendNews(com.pipoll.entity.trendnewsendpoint.model.TrendNews content) throws java.io.IOException {
    InsertTrendNews result = new InsertTrendNews(content);
    initialize(result);
    return result;
  }

  public class InsertTrendNews extends TrendnewsendpointRequest<com.pipoll.entity.trendnewsendpoint.model.TrendNews> {

    private static final String REST_PATH = "trendnews";

    /**
     * Create a request for the method "insertTrendNews".
     *
     * This request holds the parameters needed by the the trendnewsendpoint server.  After setting
     * any optional parameters, call the {@link InsertTrendNews#execute()} method to invoke the remote
     * operation. <p> {@link InsertTrendNews#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.pipoll.entity.trendnewsendpoint.model.TrendNews}
     * @since 1.13
     */
    protected InsertTrendNews(com.pipoll.entity.trendnewsendpoint.model.TrendNews content) {
      super(Trendnewsendpoint.this, "POST", REST_PATH, content, com.pipoll.entity.trendnewsendpoint.model.TrendNews.class);
    }

    @Override
    public InsertTrendNews setAlt(java.lang.String alt) {
      return (InsertTrendNews) super.setAlt(alt);
    }

    @Override
    public InsertTrendNews setFields(java.lang.String fields) {
      return (InsertTrendNews) super.setFields(fields);
    }

    @Override
    public InsertTrendNews setKey(java.lang.String key) {
      return (InsertTrendNews) super.setKey(key);
    }

    @Override
    public InsertTrendNews setOauthToken(java.lang.String oauthToken) {
      return (InsertTrendNews) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertTrendNews setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertTrendNews) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertTrendNews setQuotaUser(java.lang.String quotaUser) {
      return (InsertTrendNews) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertTrendNews setUserIp(java.lang.String userIp) {
      return (InsertTrendNews) super.setUserIp(userIp);
    }

    @Override
    public InsertTrendNews set(String parameterName, Object value) {
      return (InsertTrendNews) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listTrendNews".
   *
   * This request holds the parameters needed by the trendnewsendpoint server.  After setting any
   * optional parameters, call the {@link ListTrendNews#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListTrendNews listTrendNews() throws java.io.IOException {
    ListTrendNews result = new ListTrendNews();
    initialize(result);
    return result;
  }

  public class ListTrendNews extends TrendnewsendpointRequest<com.pipoll.entity.trendnewsendpoint.model.CollectionResponseTrendNews> {

    private static final String REST_PATH = "trendnews";

    /**
     * Create a request for the method "listTrendNews".
     *
     * This request holds the parameters needed by the the trendnewsendpoint server.  After setting
     * any optional parameters, call the {@link ListTrendNews#execute()} method to invoke the remote
     * operation. <p> {@link ListTrendNews#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListTrendNews() {
      super(Trendnewsendpoint.this, "GET", REST_PATH, null, com.pipoll.entity.trendnewsendpoint.model.CollectionResponseTrendNews.class);
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
    public ListTrendNews setAlt(java.lang.String alt) {
      return (ListTrendNews) super.setAlt(alt);
    }

    @Override
    public ListTrendNews setFields(java.lang.String fields) {
      return (ListTrendNews) super.setFields(fields);
    }

    @Override
    public ListTrendNews setKey(java.lang.String key) {
      return (ListTrendNews) super.setKey(key);
    }

    @Override
    public ListTrendNews setOauthToken(java.lang.String oauthToken) {
      return (ListTrendNews) super.setOauthToken(oauthToken);
    }

    @Override
    public ListTrendNews setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListTrendNews) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListTrendNews setQuotaUser(java.lang.String quotaUser) {
      return (ListTrendNews) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListTrendNews setUserIp(java.lang.String userIp) {
      return (ListTrendNews) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListTrendNews setCursor(java.lang.String cursor) {
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

    public ListTrendNews setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListTrendNews set(String parameterName, Object value) {
      return (ListTrendNews) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeTrendNews".
   *
   * This request holds the parameters needed by the trendnewsendpoint server.  After setting any
   * optional parameters, call the {@link RemoveTrendNews#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveTrendNews removeTrendNews(java.lang.String id) throws java.io.IOException {
    RemoveTrendNews result = new RemoveTrendNews(id);
    initialize(result);
    return result;
  }

  public class RemoveTrendNews extends TrendnewsendpointRequest<Void> {

    private static final String REST_PATH = "trendnews/{id}";

    /**
     * Create a request for the method "removeTrendNews".
     *
     * This request holds the parameters needed by the the trendnewsendpoint server.  After setting
     * any optional parameters, call the {@link RemoveTrendNews#execute()} method to invoke the remote
     * operation. <p> {@link RemoveTrendNews#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveTrendNews(java.lang.String id) {
      super(Trendnewsendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveTrendNews setAlt(java.lang.String alt) {
      return (RemoveTrendNews) super.setAlt(alt);
    }

    @Override
    public RemoveTrendNews setFields(java.lang.String fields) {
      return (RemoveTrendNews) super.setFields(fields);
    }

    @Override
    public RemoveTrendNews setKey(java.lang.String key) {
      return (RemoveTrendNews) super.setKey(key);
    }

    @Override
    public RemoveTrendNews setOauthToken(java.lang.String oauthToken) {
      return (RemoveTrendNews) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveTrendNews setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveTrendNews) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveTrendNews setQuotaUser(java.lang.String quotaUser) {
      return (RemoveTrendNews) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveTrendNews setUserIp(java.lang.String userIp) {
      return (RemoveTrendNews) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String id;

    /**

     */
    public java.lang.String getId() {
      return id;
    }

    public RemoveTrendNews setId(java.lang.String id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveTrendNews set(String parameterName, Object value) {
      return (RemoveTrendNews) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateTrendNews".
   *
   * This request holds the parameters needed by the trendnewsendpoint server.  After setting any
   * optional parameters, call the {@link UpdateTrendNews#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.pipoll.entity.trendnewsendpoint.model.TrendNews}
   * @return the request
   */
  public UpdateTrendNews updateTrendNews(com.pipoll.entity.trendnewsendpoint.model.TrendNews content) throws java.io.IOException {
    UpdateTrendNews result = new UpdateTrendNews(content);
    initialize(result);
    return result;
  }

  public class UpdateTrendNews extends TrendnewsendpointRequest<com.pipoll.entity.trendnewsendpoint.model.TrendNews> {

    private static final String REST_PATH = "trendnews";

    /**
     * Create a request for the method "updateTrendNews".
     *
     * This request holds the parameters needed by the the trendnewsendpoint server.  After setting
     * any optional parameters, call the {@link UpdateTrendNews#execute()} method to invoke the remote
     * operation. <p> {@link UpdateTrendNews#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.pipoll.entity.trendnewsendpoint.model.TrendNews}
     * @since 1.13
     */
    protected UpdateTrendNews(com.pipoll.entity.trendnewsendpoint.model.TrendNews content) {
      super(Trendnewsendpoint.this, "PUT", REST_PATH, content, com.pipoll.entity.trendnewsendpoint.model.TrendNews.class);
    }

    @Override
    public UpdateTrendNews setAlt(java.lang.String alt) {
      return (UpdateTrendNews) super.setAlt(alt);
    }

    @Override
    public UpdateTrendNews setFields(java.lang.String fields) {
      return (UpdateTrendNews) super.setFields(fields);
    }

    @Override
    public UpdateTrendNews setKey(java.lang.String key) {
      return (UpdateTrendNews) super.setKey(key);
    }

    @Override
    public UpdateTrendNews setOauthToken(java.lang.String oauthToken) {
      return (UpdateTrendNews) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateTrendNews setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateTrendNews) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateTrendNews setQuotaUser(java.lang.String quotaUser) {
      return (UpdateTrendNews) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateTrendNews setUserIp(java.lang.String userIp) {
      return (UpdateTrendNews) super.setUserIp(userIp);
    }

    @Override
    public UpdateTrendNews set(String parameterName, Object value) {
      return (UpdateTrendNews) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Trendnewsendpoint}.
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

    /** Builds a new instance of {@link Trendnewsendpoint}. */
    @Override
    public Trendnewsendpoint build() {
      return new Trendnewsendpoint(this);
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
     * Set the {@link TrendnewsendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setTrendnewsendpointRequestInitializer(
        TrendnewsendpointRequestInitializer trendnewsendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(trendnewsendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
