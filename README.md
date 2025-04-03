<html>
<h3>Contents</h3>
<a href="#a1">Features</a><br/>
<a href="#a2">Login</a><br/>
<a href="#a3">Main - Basedata</a><br/>
<a href="#a4">Main - Interfaces</a><br/>
<a href="#a5">Interface - Interface data</a><br/>
<a href="#a6">Interface - Methods</a><br/>
<a href="#a7">Interface - Method</a><br/>
<a href="#a8">Interface - Method - Mockdata overview</a><br/>
<a href="#a9">Interface - Method - Mockdata</a><br/>
<a href="#a10">Usage / Integration MoSy</a><br/>
<a href="#a11">Main - Records</a><br/>
<a href="#a12">Main - Record</a><br/>
<a href="#a13">Main / Interface-method - Upload mockdata</a><br/>
<a href="#a14">Interface - Method - Recordconfigs</a><br/>
<a href="#a15">Interface - Method - Recordconfig</a><br/>
<a href="#a16">Main - Mockprofiles</a><br/>
<a href="#a17">Mockprofile</a><br/>
<a href="#a18">Multi-tenancy</a><br/>
<a href="#a19">Import / Export</a><br/>
<a href="#a20">API</a><br/>
<a href="#a21">API-Client</a><br/>
<a href="#a22">Property files</a><br/>
<a href="#a23">Runtime environments � Spring Boot</a><br/>
<a href="#a24">Technical Architecture</a><br/>
 
<br/><br/><br/>
<a name="a1"/>
<p><strong><u>Features</u></strong></p>
<ul>
<li>Mock all kind of interfaces
	<ul>
		<li>REST</li>
		<li>SOAP</li>
		<li>CUSTOM (XML / JSON / OTHER)
			<ul>
				<li>(Requires some implementation in calling software)</li>
				<ul><li>Predefined abstract class - see class <a href="https://github.com/joergdev/MoSy-API-client/blob/master/src/main/java/de/joergdev/mosy/api/client/AbstractCustomMockImpl.java">AbstractCustomMockImpl</a> (javadoc)</li></ul>
				<li>Examples: MQ-Interfaces, RMI-Interfaces, Native-Interfaces, &hellip;</li>
			</ul>
		</li>
		<li>Configurable global, for interface or only for method</li>
	</ul>
</li>

<br/><br/>

<li>Record calls and use as mockdata
	<ul>
		<li>Configurable global, for interface, for method or only on defined requests</li>
		<li>Possibilty to define recordsession for separated records</li>
	</ul>
</li>

<br/><br/>

<li>Possibilty to define mockprofiles with isolated mockdata
	<ul>
		<li>Can be used for&hellip;
			<ul>
				<li>automatic tests for example where some tests needs different interface behavior by having same input data</li>
				<li>(developer) tests wihout side effects to others</li>
			</ul>
		</li>
	</ul>
</li>

<br/><br/>

<li>Open Rest API
	<ul>
		<li>Only one public API, used by webfronted as well</li>
	</ul>
</li>

<br/><br/>

<li>Runable as spring-boot-app - standalone or within container</li>

</ul>

<br/><br/><br/><br/><br/><br/>

<a name="a2"/>
<p><strong><u>Login</u></strong></p>
<img src="doc/login.png" />
<p>Default password: &bdquo;m0sy&ldquo;</p>
<br/><br/><br/><br/><br/><br/>

<a name="a3"/>
<p><strong><u>Main - Basedata</u></strong></p>
<img src="doc/main_basedata.png" />
<br/><br/>
<ul>
<li>Global config for these values (Yes / No / Individually by interface)
<ul>
<li>&bdquo;Mock active&ldquo;
<ul>
<li>&bdquo;On startup&ldquo;
<ul>
<li>Overwrites &bdquo;Mock active&ldquo; on next Backend Boot or on &bdquo;Reset System&ldquo;</li>
</ul>
</li>
<li>&bdquo;Routing on no mockdata&ldquo;</li>
<li>&bdquo;Record&ldquo;</li>
</ul>
</li>
</ul>
</li>
</ul>
<br/>
<ul>
<li>&bdquo;Reset system&ldquo;
<ul>
<li>Reset all config to default (&bdquo;On startup&ldquo;)</li>
<li>Reset count calls</li>
<li>Delete temporary data (non persistent mockprofiles and recordsessions)</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a4"/>
<p><strong><u>Main &ndash; Interfaces</u></strong></p>
<img src="doc/main_interfaces.png" />
<ul>
<li>Tree
<ul>
<li>Overview of interfaces grouped by type (SOAP / REST / CUSTOM)</li>
<li>Left click on single interface =&gt; Show / Edit</li>
</ul>
</li>
<li>Table
<ul>
<li>Overview of all interfaces with possibilty to show/edit / delete or creation of a new interface</li>
<li>Columns
<ul>
<li>Type
<ul>
<li>SOAP / REST / CUSTOM_XML / CUSTOM_JSON / CUSTOM_PLAIN</li>
<li>Left click on single interface =&gt; Show / Edit</li>
</ul>
</li>
<li>Name</li>
<li>Mock active
<ul>
<li>True / false / empty (Individually by method)</li>
</ul>
</li>
<li>Record
<ul>
<li>True / false / empty (Individually by method)</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a5"/>
<p><strong><u>Interface &ndash; Interface data</u></strong></p>
<p><em>REST</em></p>
<img src="doc/ifc_data_rest.png" />
<p><em>SOAP</em></p>
<img src="doc/ifc_data_soap.png" />
<p><em>CUSTOM</em></p>
<img src="doc/ifc_data_custom.png" />
<ul>
<li>Name</li>
<li>Type
<ul>
<li>REST / SOAP / CUSTOM_XML / CUSTOM_JSON / CUSTOM_PLAIN</li>
</ul>
</li>
<li>Service path
<ul>
<li>REST: HTTP (base)path</li>
<li>SOAP: Service endpoint (in most cases equal to service name)</li>
<li>CUSTOM(XML/JSON/PLAIN): NOT visible</li>
</ul>
</li>
<li>Mock active
<ul>
<li>On startup</li>
</ul>
</li>
<li>Routing on no mockdata</li>
<li>Routing URL
<ul>
<li>Endpoint for routing on no mock (delegate to real service)</li>
<li>NOT visible for type CUSTOM(XML/JSON/PLAIN)</li>
</ul>
</li>
<li>Record</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a6"/>
<p><strong><u>Interface - Methods</u></strong></p>
<img src="doc/ifc_methods.png" />
<ul>
<li>Tree
<ul>
<li>Overview of methods</li>
<li>Left click on single method =&gt; Show / Edit</li>
</ul>
</li>
<li>Table
<ul>
<li>Overview of all methods with possibilty to show/edit / delete or creation of a new method</li>
<li>Columns
<ul>
<li>Name</li>
<li>Mock active
<ul>
<li>True / false</li>
</ul>
</li>
<li>Routing on no mockdata
<ul>
<li>True / false</li>
</ul>
</li>
<li>Record
<ul>
<li>True / false / empty (Individually by recordconfig)</li>
</ul>
</li>
<li>Count calls</li>
</ul>
</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a7"/>
<p><strong><u>Interface - Method</u></strong></p>
<img src="doc/ifc_method.png" />
<ul>
<li>Name</li>
<li>Service path
<ul>
<li>REST: HTTP (sub)path, may be empty</li>
<li>SOAP: Root XML tag name, may not be empty</li>
<li>CUSTOM(XML/JSON/PLAIN): NOT visible</li>
</ul>
</li>
<li>HTTP method
<ul>
<li>Only visible for REST Services</li>
</ul>
</li>
<li>Mock active
<ul>
<li>On startup</li>
</ul>
</li>
<li>Routing on no mockdata</li>
<li>Record
<ul>
<li>Yes (global) / No (global) / Individually by recordconfig</li>
</ul>
</li>
<li>Count calls</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a8"/>
<p><strong><u>Interface - Method- Mockdata overview</u></strong></p>
<img src="doc/method_mockdata.png" />
<ul>
<li>Mockprofiles
<ul>
<li>Set if mockprofile(s) assigned (see chapter for mockprofile)</li>
</ul>
</li>
<li>Title</li>
<li>Active
<ul>
<li>True / false</li>
</ul>
</li>
<li>Count calls</li>
<li>Created</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a9"/>
<p><strong><u>Interface - Method- Mockdata</u></strong></p>
<img src="doc/mockdata.png" />
<ul>
<li>Mockprofiles (Table)
<ul>
<li>(Optional)</li>
<li>Assigned mockprofile(s) (see chapter for mockprofile)</li>
</ul>
</li>
<li>Title</li>
<li>Active</li>
<li>Common (Use for assingned mockprofiles and / or global)</li>
<li>Path params (REST)</li>
<li>URL arguments (REST)</li>
<li>Request</li>
<li>Response</li>
<li>Delay (ms.) - optional to simulate slow(er) response time</li>
<li>Created</li>
<li>Count calls</li>
<br/>
<li>Hashcode of request und response gets saved</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a10"/>
<p><strong><u>Usage / Integration MoSy</u></strong></p>
<ul>
<li>SOAP
<ul>
<li>Change Endpoint (<a href="http://real-endpoint/XyzService">http://real-endpoint/XyzService</a>) to <a href="http://mosy_base_url/soap/XyzService">http://mosy_base_url/soap/XyzService</a></li>
</ul>
</li>
<li>REST
<ul>
<li>Change Endpoint (<a href="http://real-endpoint/resource/id">http://real-endpoint/resource/id</a>) to <a href="http://mosy_base_url/rest/resource/id">http://mosy_base_url/rest/resource/id</a></li>
</ul>
</li>
<li>CUSTOM
<ul>
<li>Needs custom implementation in calling software</li>
<li>See MosyApiClient#customRequest
<ul>
<li>IN-Parameter
<ul>
<li>Interface name</li>
<li>Method name</li>
<li>Request</li>
</ul>
</li>
<li>OUT-Parameter
<ul>
<li>Record (boolean)
<ul>
<li>See MosyApiClient#saveRecord</li>
</ul>
</li>
<li>Route (boolean)</li>
<li>Response</li>
<li>Method (Object)
<ul>
<li>Required for saving record possibly</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a11"/>
<p><strong><u>Main &ndash; Recordsessions</u></strong></p>
<img src="doc/main_recordsessions.png" />
<ul>
<li>Overview of all recordsessions with possibilty to create new or delete.</li>
<br/>
<li>ID</li>
<li>Created</li>
</ul>
<br/><br/><br/><br/><br/><br/>


<a name="a11"/>
<p><strong><u>Main &ndash; Records</u></strong></p>
<img src="doc/main_records.png" />
<ul>
<li>Overview of all records with possibilty to download, use as mockdata, show or delete.</li>
<br/>
<li>Filter Recordsession (optional)</li>
<br/>
<li>Interface</li>
<li>Method</li>
<li>Created</li>
</ul>
<ul>
<li>Download
<ul>
<li>Download record as .txt or .zip (on multiple choice)</li>
</ul>
</li>
</ul>
<ul>
<li>Use as mockdata</li>
<img src="doc/record_use_as_mockdata.png" />
</ul>
<ul>
<li>Record saved as mockdata for interface SoapService and method getEntries</li>
<img src="doc/record_saved_as_mockdata.png" />
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a12"/>
<p><strong><u>Main - Record</u></strong></p>
<img src="doc/record.png" />
<ul>
<li>Recordsession</li>
<li>Interface</li>
<li>Method</li>
<li>Created</li>
<li>Path params (REST)</li>
<li>URL arguments (REST)</li>
<li>Request</li>
<li>Response</li>
</ul>
<ul>
<li>Use as mockdata
<ul>
<li>See above</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a13"/>
<p><strong><u>Main / Interface-method - Upload mockdata</u></strong></p>
<img src="doc/upload_mockdata.png" />
<p>* If upload from interface method interface and method gets prefilled</p>
<ul>
<br/><br/>
<li>Choose</li>
<img src="doc/upload_mockdata_2.png" />
</ul>
<ul>
<li>Upload</li>
<li>Mockdata gets saved</li>
</ul>
<ul>
<li>Filename format (otional otherwise interface and method must be provided in GUI):
<ul>
<li>[Interface-name]_[method-name]_*</li>
</ul>
</li>
</ul>
<ul>
<li>File content (example)</li>
</ul>
<p>&gt;&gt;&gt;&gt;&gt;&gt;REQUEST&gt;</p>
<p>&lt;?xml version="1.0" ?&gt;&lt;S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"&gt;&lt;S:Body&gt;&lt;ns2:testMethod xmlns:ns2="http://services.test.mosy.joergdev.github.com/"&gt;&lt;action&gt;upload_main&lt;/action&gt;&lt;/ns2:testMethod&gt;&lt;/S:Body&gt;&lt;/S:Envelope&gt;</p>
<p>&gt;&gt;&gt;&gt;&gt;&gt;RESPONSE&gt;</p>
<p>&lt;?xml version="1.0" ?&gt;&lt;S:Envelope xmlns:S="http://schemas.xmlsoap.org/soap/envelope/"&gt;&lt;S:Body&gt;&lt;ns2:testMethodResponse xmlns:ns2="http://services.test.mosy.joergdev.github.com/"&gt;&lt;return&gt;!ERROR!&lt;/return&gt;&lt;/ns2:testMethodResponse&gt;&lt;/S:Body&gt;&lt;/S:Envelope&gt;</p>
<br/><br/><br/><br/><br/><br/>

<a name="a14"/>
<p><strong><u>Interface - Method - Recordconfigs</u></strong></p>
<img src="doc/recordconfigs.png" />
<ul>
<li>Title</li>
<li>Active (true/false)</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a15"/>
<p><strong><u>Interface - Method - Recordconfig</u></strong></p>
<img src="doc/recordconfig.png" />
<ul>
<li>Title</li>
<li>Active</li>
<li>Requestdata
<ul>
<li>Request get recorded if matches (even if request contains more data)</li>
</ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a16"/>
<p><strong><u>Main - Mockprofiles</u></strong></p>
<img src="doc/mockprofiles.png" />
<ul>
<li>Name</li>
<li>Persistent</li>
<li>Use common mocks</li>
<br/>
<li>On delete all non common mockdata assigned to only this mockprofile get deleted too</li>
</ul>
<br/><br/>
<p><u>Usage (mockdata)</u></p>
<img src="doc/mockprofile_usage_mockdata.png" />
<ul>
<li>Mockdata is only valid for requests with defined mockprofile(s)</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a17"/>
<p><strong><u>Mockprofile</u></strong></p>
<img src="doc/mockprofile.png" />
<ul>
<li>Name</li>
<li>Persistent (if false delete on system boot)</li>
<li>Use common mocks (if true use global mocks)</li>
<li>Description</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a18"/>
<p><strong><u>Multi-tenancy</u></strong></p>
<ul>
  <li>With multi-tenancy mode activated, you can configure all data isolated for your tenant</li>
  <li>By default, multi-tenancy mode is not activated
    <ul>
      <li>Activate by setting the system/environment property "MOSY_MULTI_TENANCY_ENABLED" or the property "multi_tenancy_enabled" in mosy_backend.properties</li>
    </ul>
  </li>
  <li>Mocking / Integration
    <ul>
    	<li>Soap / Rest calls: Set tenantId in HTTP Header (see de.joergdev.mosy.api.APIConstants.HTTP_HEADER_TENANT_ID)
    	  <ul>
    	    <li>Alternative (without HTTP header): call via url (for example, SOAP) .../<b>tenants/{tenantId}/</b>mock-services/soap/SoapService</li>
    	  </ul>
    	</li>
    	<li>If you want to use the API(client) you have to log in with the tenantId</li>
    </ul>
  </li>
</ul>
<br/>
<p><u>Login with tenant choose / creation of tenant</u></p>
<img src="doc/login_multi_tenancy.png" />
<img src="doc/login_tenant_choose.png" />
<br/>
<img src="doc/tenant_create.png" />
<br/><br/><br/>
<p><u>Edit / delete tenant (after login)</u></p>
<img src="doc/main_edit_tenant_data.png" />
<br/>
<img src="doc/tenant_edit.png" />
<br/><br/><br/><br/><br/><br/>


<a name="a19"/>
<p><strong><u>Import / Export</u></strong></p>

<ul>
	<li>Possible use-cases
		<ul>
			<li>Copy data for another tenant</li>
			<li>Staging</li>
			<li>Backup</li>
		</ul>
	</li>
	<li>Export
		<ul>
			<li>Export interfaces including methods</li>
			<li>See MosyApiClient#exportDataToFile</li>
		</ul>
	</li>
	<li>Import
		<ul>
			<li>Import interfaces including methods</li>
			<li>See MosyApiClient#importData or API doc /mosy/api/v_5_0/system/import-data</li>
		</ul>
	</li>
</ul>
<br/>
<br/><br/><br/><br/><br/><br/>


<a name="a20"/>
<p><strong><u>API</u></strong></p>
[OpenAPI Description](doc/api.json)
<br/>
<br/><br/><br/><br/><br/><br/>


<a name="a21"/>
<p><strong><u>API-Client</u></strong></p>
<ul>
<li>Allows you to easily integrate MoSy into your apps and processes</li>
</ul>
<br/>
<ul>
<li>See class MosyApiClient
<ul>
<li>Used by webfrontend as well</li>
</ul>
<br/>
<img src="doc/api_client.png" />
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a22"/>
<p><strong><u>Properties</u></strong></p>
<br/>
The following Properties can be defined as system properties, environment properties or by property file and are read in this order.
<br/><br/>
<ul>
<li>mosy-backend
  <ul>
    <li>mosy_backend.properties
      <ul>
        <li>login_secret
          <ul>
            <li>default = &bdquo;m0sy&ldquo;</li>
          </ul>
          <ul>
            <li>system/environment property = &bdquo;MOSY_LOGIN_SECRET&ldquo;</li>
          </ul>
        </li>
        <li>multi_tenancy_enabled
          <ul>
            <li>default = false</li>
          </ul>
          <ul>
            <li>system/environment property = &bdquo;MOSY_MULTI_TENANCY_ENABLED&ldquo;</li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</li>
<li>mosy-api-client
  <ul>
    <li>mosy_api_client.properties
      <ul>
        <li>api_endpoint
          <ul>
            <li>default = <a href="http://localhost:3911/mosy/api/v_5_0">http://localhost:3911/mosy/api/v_5_0</a></li>
          </ul>
          <ul>
            <li>system/environment property = &bdquo;MOSY_API_ENDPOINT&ldquo;</li>
          </ul>
         </li>
      </ul>
    </li>
  </ul>
</li>
<li>mosy-frontend
  <ul>
    <li>mosy_frontend.properties
      <ul>
        <li>upload_mockdata_singlemode
          <ul>
            <li>defaultvalue = &bdquo;true&ldquo; (false is currently not supported)</li>
          </ul>
        </li>
      </ul>
    </li>
  </ul>
</li>
</ul>
<br/><br/><br/><br/><br/><br/>

<a name="a23"/>
<p><strong><u>Runtime environments &ndash; Spring Boot</u></strong></p>
<p>Spring-boot Version: 3.4.3</p>
<p>Java Version: 21 (since 5.0.0, before 8)</p>
<br/>
<p><u>Projects:</u></p>
<ul>
  <li>mosy-backend-standalone
    <ul>
      <li>Properties
        <ul>
          <li>META-INF/persistence.xml</li>
            <li>application.properties
              <ul>
                <li>server.port: 3911</li>
                <ul>
                	<li>Could be changed by setting "-Dserver.port=xx"</li>
                </ul>
              </ul>
            </li>
            <li>log4j.xml</li>
          </li>
        </ul>
      </li>
    </ul>
  </li>
  <li>mosy-frontend-standalone
    <ul>
      <li>JVM Arguments
      	<ul>
      		<li><span style="color:red"><b>IMPORTANT: Set "-Dorg.apache.el.parser.SKIP_IDENTIFIER_CHECK=true"</b></span>.</li>
      	</ul>
      </li>
      <li>Properties
        <ul>
          <li>META-INF/faces-config.xml</li>
          <li>application.properties
            <ul>
              <li>server.port: 8087</li>
              <ul>
              	<li>Could be changed by setting "-Dserver.port=xx"</li>
              </ul>
            </ul>
          </li>
          <li>log4j.xml</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
<br/><br/><br/><br/><br/><br/>


<a name="a24"/>
<p><strong><u>Technical Architecture</u></strong></p>
<br/>
<img src="doc/mosy_arc.png" />
</html>