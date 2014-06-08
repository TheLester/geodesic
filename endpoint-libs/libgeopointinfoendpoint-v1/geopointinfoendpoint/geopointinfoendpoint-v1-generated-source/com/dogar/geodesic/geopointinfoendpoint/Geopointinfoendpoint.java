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
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-06-08 at 11:15:20 UTC 
 * Modify at your own risk.
 */

package com.dogar.geodesic.geopointinfoendpoint;

/**
 * Service definition for Geopointinfoendpoint (v1).
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
 * This service uses {@link GeopointinfoendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Geopointinfoendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the geopointinfoendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myapp.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "geopointinfoendpoint/v1/";

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
  public Geopointinfoendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Geopointinfoendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getGeoPointInfo".
   *
   * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
   * any optional parameters, call the {@link GetGeoPointInfo#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetGeoPointInfo getGeoPointInfo(java.lang.Long id) throws java.io.IOException {
    GetGeoPointInfo result = new GetGeoPointInfo(id);
    initialize(result);
    return result;
  }

  public class GetGeoPointInfo extends GeopointinfoendpointRequest<com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo> {

    private static final String REST_PATH = "geopointinfo/{id}";

    /**
     * Create a request for the method "getGeoPointInfo".
     *
     * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
     * any optional parameters, call the {@link GetGeoPointInfo#execute()} method to invoke the remote
     * operation. <p> {@link GetGeoPointInfo#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetGeoPointInfo(java.lang.Long id) {
      super(Geopointinfoendpoint.this, "GET", REST_PATH, null, com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo.class);
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
    public GetGeoPointInfo setAlt(java.lang.String alt) {
      return (GetGeoPointInfo) super.setAlt(alt);
    }

    @Override
    public GetGeoPointInfo setFields(java.lang.String fields) {
      return (GetGeoPointInfo) super.setFields(fields);
    }

    @Override
    public GetGeoPointInfo setKey(java.lang.String key) {
      return (GetGeoPointInfo) super.setKey(key);
    }

    @Override
    public GetGeoPointInfo setOauthToken(java.lang.String oauthToken) {
      return (GetGeoPointInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public GetGeoPointInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetGeoPointInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetGeoPointInfo setQuotaUser(java.lang.String quotaUser) {
      return (GetGeoPointInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetGeoPointInfo setUserIp(java.lang.String userIp) {
      return (GetGeoPointInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetGeoPointInfo setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetGeoPointInfo set(String parameterName, Object value) {
      return (GetGeoPointInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertGeoPointInfo".
   *
   * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
   * any optional parameters, call the {@link InsertGeoPointInfo#execute()} method to invoke the
   * remote operation.
   *
   * @param content the {@link com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo}
   * @return the request
   */
  public InsertGeoPointInfo insertGeoPointInfo(com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo content) throws java.io.IOException {
    InsertGeoPointInfo result = new InsertGeoPointInfo(content);
    initialize(result);
    return result;
  }

  public class InsertGeoPointInfo extends GeopointinfoendpointRequest<com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo> {

    private static final String REST_PATH = "geopointinfo";

    /**
     * Create a request for the method "insertGeoPointInfo".
     *
     * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
     * any optional parameters, call the {@link InsertGeoPointInfo#execute()} method to invoke the
     * remote operation. <p> {@link InsertGeoPointInfo#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo}
     * @since 1.13
     */
    protected InsertGeoPointInfo(com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo content) {
      super(Geopointinfoendpoint.this, "POST", REST_PATH, content, com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo.class);
    }

    @Override
    public InsertGeoPointInfo setAlt(java.lang.String alt) {
      return (InsertGeoPointInfo) super.setAlt(alt);
    }

    @Override
    public InsertGeoPointInfo setFields(java.lang.String fields) {
      return (InsertGeoPointInfo) super.setFields(fields);
    }

    @Override
    public InsertGeoPointInfo setKey(java.lang.String key) {
      return (InsertGeoPointInfo) super.setKey(key);
    }

    @Override
    public InsertGeoPointInfo setOauthToken(java.lang.String oauthToken) {
      return (InsertGeoPointInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertGeoPointInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertGeoPointInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertGeoPointInfo setQuotaUser(java.lang.String quotaUser) {
      return (InsertGeoPointInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertGeoPointInfo setUserIp(java.lang.String userIp) {
      return (InsertGeoPointInfo) super.setUserIp(userIp);
    }

    @Override
    public InsertGeoPointInfo set(String parameterName, Object value) {
      return (InsertGeoPointInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listGeoPointInfo".
   *
   * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
   * any optional parameters, call the {@link ListGeoPointInfo#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListGeoPointInfo listGeoPointInfo() throws java.io.IOException {
    ListGeoPointInfo result = new ListGeoPointInfo();
    initialize(result);
    return result;
  }

  public class ListGeoPointInfo extends GeopointinfoendpointRequest<com.dogar.geodesic.geopointinfoendpoint.model.CollectionResponseGeoPointInfo> {

    private static final String REST_PATH = "geopointinfo";

    /**
     * Create a request for the method "listGeoPointInfo".
     *
     * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
     * any optional parameters, call the {@link ListGeoPointInfo#execute()} method to invoke the
     * remote operation. <p> {@link ListGeoPointInfo#initialize(com.google.api.client.googleapis.servi
     * ces.AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListGeoPointInfo() {
      super(Geopointinfoendpoint.this, "GET", REST_PATH, null, com.dogar.geodesic.geopointinfoendpoint.model.CollectionResponseGeoPointInfo.class);
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
    public ListGeoPointInfo setAlt(java.lang.String alt) {
      return (ListGeoPointInfo) super.setAlt(alt);
    }

    @Override
    public ListGeoPointInfo setFields(java.lang.String fields) {
      return (ListGeoPointInfo) super.setFields(fields);
    }

    @Override
    public ListGeoPointInfo setKey(java.lang.String key) {
      return (ListGeoPointInfo) super.setKey(key);
    }

    @Override
    public ListGeoPointInfo setOauthToken(java.lang.String oauthToken) {
      return (ListGeoPointInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public ListGeoPointInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListGeoPointInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListGeoPointInfo setQuotaUser(java.lang.String quotaUser) {
      return (ListGeoPointInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListGeoPointInfo setUserIp(java.lang.String userIp) {
      return (ListGeoPointInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListGeoPointInfo setCursor(java.lang.String cursor) {
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

    public ListGeoPointInfo setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListGeoPointInfo set(String parameterName, Object value) {
      return (ListGeoPointInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeGeoPointInfo".
   *
   * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
   * any optional parameters, call the {@link RemoveGeoPointInfo#execute()} method to invoke the
   * remote operation.
   *
   * @param id
   * @return the request
   */
  public RemoveGeoPointInfo removeGeoPointInfo(java.lang.Long id) throws java.io.IOException {
    RemoveGeoPointInfo result = new RemoveGeoPointInfo(id);
    initialize(result);
    return result;
  }

  public class RemoveGeoPointInfo extends GeopointinfoendpointRequest<Void> {

    private static final String REST_PATH = "geopointinfo/{id}";

    /**
     * Create a request for the method "removeGeoPointInfo".
     *
     * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
     * any optional parameters, call the {@link RemoveGeoPointInfo#execute()} method to invoke the
     * remote operation. <p> {@link RemoveGeoPointInfo#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveGeoPointInfo(java.lang.Long id) {
      super(Geopointinfoendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveGeoPointInfo setAlt(java.lang.String alt) {
      return (RemoveGeoPointInfo) super.setAlt(alt);
    }

    @Override
    public RemoveGeoPointInfo setFields(java.lang.String fields) {
      return (RemoveGeoPointInfo) super.setFields(fields);
    }

    @Override
    public RemoveGeoPointInfo setKey(java.lang.String key) {
      return (RemoveGeoPointInfo) super.setKey(key);
    }

    @Override
    public RemoveGeoPointInfo setOauthToken(java.lang.String oauthToken) {
      return (RemoveGeoPointInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveGeoPointInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveGeoPointInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveGeoPointInfo setQuotaUser(java.lang.String quotaUser) {
      return (RemoveGeoPointInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveGeoPointInfo setUserIp(java.lang.String userIp) {
      return (RemoveGeoPointInfo) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveGeoPointInfo setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveGeoPointInfo set(String parameterName, Object value) {
      return (RemoveGeoPointInfo) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateGeoPointInfo".
   *
   * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
   * any optional parameters, call the {@link UpdateGeoPointInfo#execute()} method to invoke the
   * remote operation.
   *
   * @param content the {@link com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo}
   * @return the request
   */
  public UpdateGeoPointInfo updateGeoPointInfo(com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo content) throws java.io.IOException {
    UpdateGeoPointInfo result = new UpdateGeoPointInfo(content);
    initialize(result);
    return result;
  }

  public class UpdateGeoPointInfo extends GeopointinfoendpointRequest<com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo> {

    private static final String REST_PATH = "geopointinfo";

    /**
     * Create a request for the method "updateGeoPointInfo".
     *
     * This request holds the parameters needed by the the geopointinfoendpoint server.  After setting
     * any optional parameters, call the {@link UpdateGeoPointInfo#execute()} method to invoke the
     * remote operation. <p> {@link UpdateGeoPointInfo#initialize(com.google.api.client.googleapis.ser
     * vices.AbstractGoogleClientRequest)} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo}
     * @since 1.13
     */
    protected UpdateGeoPointInfo(com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo content) {
      super(Geopointinfoendpoint.this, "PUT", REST_PATH, content, com.dogar.geodesic.geopointinfoendpoint.model.GeoPointInfo.class);
    }

    @Override
    public UpdateGeoPointInfo setAlt(java.lang.String alt) {
      return (UpdateGeoPointInfo) super.setAlt(alt);
    }

    @Override
    public UpdateGeoPointInfo setFields(java.lang.String fields) {
      return (UpdateGeoPointInfo) super.setFields(fields);
    }

    @Override
    public UpdateGeoPointInfo setKey(java.lang.String key) {
      return (UpdateGeoPointInfo) super.setKey(key);
    }

    @Override
    public UpdateGeoPointInfo setOauthToken(java.lang.String oauthToken) {
      return (UpdateGeoPointInfo) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateGeoPointInfo setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateGeoPointInfo) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateGeoPointInfo setQuotaUser(java.lang.String quotaUser) {
      return (UpdateGeoPointInfo) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateGeoPointInfo setUserIp(java.lang.String userIp) {
      return (UpdateGeoPointInfo) super.setUserIp(userIp);
    }

    @Override
    public UpdateGeoPointInfo set(String parameterName, Object value) {
      return (UpdateGeoPointInfo) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Geopointinfoendpoint}.
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

    /** Builds a new instance of {@link Geopointinfoendpoint}. */
    @Override
    public Geopointinfoendpoint build() {
      return new Geopointinfoendpoint(this);
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
     * Set the {@link GeopointinfoendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setGeopointinfoendpointRequestInitializer(
        GeopointinfoendpointRequestInitializer geopointinfoendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(geopointinfoendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
