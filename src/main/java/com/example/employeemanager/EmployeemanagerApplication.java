package com.example.employeemanager;

import com.example.employeemanager.model.Earthquake;
import com.example.employeemanager.model.EarthquakePK;
import com.example.employeemanager.repository.EarthquakeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
//import java.net.ServerSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class EmployeemanagerApplication implements CommandLineRunner {
	private String url = "http://eew-server.cwb.gov.tw:80/svc-bin/streaming/";
	private Logger logger = LoggerFactory.getLogger(EmployeemanagerApplication.class);

	@Autowired
	private EarthquakeRepo repo;
	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {

//		this.httpUrl();
//		this.mockXml();
		this.server();

//		this.client();
//		System.out.println(identifier.getTextContent());
	}
	private void mockXml() {
		String xmlStr = "<earthquake>" +
				"<identifier>CWB"+Math.random()+"</identifier>" +
				"<event>地震警報</event>" +
				"<sent>2014-01-17T12:03:30+08:00</sent>" +
				"<description>花蓮外海地震</description>" +
				"<originTime>2014-01-17T12:03:10+08:00</originTime>" +
				"<epicenter>" +
				"<epicenterLon unit=\"DEGREE\">122.19</epicenterLon>" +
				"<epicenterLat unit=\"DEGREE\">24.14</epicenterLat>" +
				"</epicenter>" +
				"<depth unit=\"KILOMETER\">13.8</depth>" +
				"<magnitude>" +
				"<magnitudeType>Mpd</magnitudeType>" +
				"<magnitudeValue>6.8</magnitudeValue>" +
				"</magnitude>" +
				"</earthquake>";

		//Use method to convert XML string content to XML Document object
		Document doc = convertStringToXMLDocument( xmlStr );
		doc.getDocumentElement().normalize();
		String identifier = doc.getElementsByTagName("identifier").item(0).getTextContent();
		String event = doc.getElementsByTagName("event").item(0).getTextContent();
		String sentTime = doc.getElementsByTagName("sent").item(0).getTextContent();
		String description = doc.getElementsByTagName("description").item(0).getTextContent();
		String originTime = doc.getElementsByTagName("originTime").item(0).getTextContent();
		BigDecimal epicenterLon = new BigDecimal(doc.getElementsByTagName("epicenterLon").item(0).getTextContent());
		BigDecimal epicenterLat = new BigDecimal(doc.getElementsByTagName("epicenterLat").item(0).getTextContent());
		BigDecimal depth = new BigDecimal(doc.getElementsByTagName("depth").item(0).getTextContent());
		String magnitudeType = doc.getElementsByTagName("magnitudeType").item(0).getTextContent();
		BigDecimal magnitudeValue = new BigDecimal(doc.getElementsByTagName("magnitudeValue").item(0).getTextContent());

		EarthquakePK id = new EarthquakePK();
		id.setIdentifier(identifier);
		Earthquake entity = new Earthquake();
		entity.setId(id);
		entity.setEvent(event);
		entity.setSentTime(sentTime);
		entity.setDescription(description);
		entity.setOriginTime(originTime);
		entity.setEpicenterLon(epicenterLon);
		entity.setEpicenterLat(epicenterLat);
		entity.setDepth(depth);
		entity.setMagnitudeType(magnitudeType);
		entity.setMagnitudeValue(magnitudeValue);
		repo.save(entity);
	}
	private Document convertStringToXMLDocument(String xmlString) {
		//Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//API to obtain DOM Document instance
		DocumentBuilder builder = null;
		try {
			//Create DocumentBuilder with default configuration
			builder = factory.newDocumentBuilder();
			//Parse the content to Document object
			Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
			return doc;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private String covertToDateTime(String dateBytes) throws Exception {
		long fromBytes = Long.parseLong(dateBytes);
		// Mask out kind and ticks
		int kind = Math.toIntExact((fromBytes >> 62) & 0x3);
		long ticks = fromBytes & 0x3FFF_FFFF_FFFF_FFFFL;
		LocalDateTime cSharpEpoch = LocalDate.of(1, Month.JANUARY, 1).atStartOfDay();
		// 100 nanosecond units or 10^-7 seconds
		final int unitsPerSecond = 10_000_000;
		long seconds = ticks / unitsPerSecond;
		long nanos = (ticks % unitsPerSecond) * 100;
		LocalDateTime ldt = cSharpEpoch.plusSeconds(seconds).plusNanos(nanos).plusHours(8);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = ldt.format(formatter);
		return formattedDateTime;
	}
	private void httpUrl() throws Exception {
		URL urlConn = new URL(url);

		String userCredentials = "CO.TSMC_H:1234abcd";
		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		HttpURLConnection connection = (HttpURLConnection) urlConn.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization",basicAuth);
		connection.connect();
		InputStream responseStream = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(responseStream);

		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();

		int index = 0;
		String boundary = "--";
		logger.info("----------- {} -----------", url);
		for (String line; (line = br.readLine()) != null; ) {
			if((index == 0 && line.contains(boundary)) ||
					line == null ||
					line.equals("") ||
					line.contains("Content") ||
//					line.contains("Date") ||
					line.contains("?xml")) {continue;}
			index = index + 1;
			if(line.contains("Date")) {
				List<String> items = Arrays.asList(line.split(" "));
				String dt = covertToDateTime(items.get(1));
				sb.append(dt+" ");
				continue;
			}
			if(!line.contains(boundary)) {
				sb.append(line);
			}
			if(line.contains(boundary)) {
				logger.info("[{}] {}", index, sb);
				sb = new StringBuffer();
			}
		}

	}

	private void server() throws IOException {
		ServerSocket ss = new ServerSocket(8888);
		while(true) {
			Socket sc = ss.accept();
			String num = String.valueOf(Math.random());
			OutputStream os = sc.getOutputStream();
			os.write("<earthquake>".getBytes(StandardCharsets.UTF_8));
			os.write(("<identifier>CWB"+num+"</identifier>").getBytes(StandardCharsets.UTF_8));
			os.write("<event>地震警報</event>".getBytes(StandardCharsets.UTF_8));
			os.write("<sent>2014-01-17T12:03:30+08:00</sent>".getBytes(StandardCharsets.UTF_8));
			os.write("<description>花蓮外海地震</description>".getBytes(StandardCharsets.UTF_8));
			os.write("<originTime>2014-01-17T12:03:10+08:00</originTime>".getBytes(StandardCharsets.UTF_8));
			os.write("<epicenter>".getBytes(StandardCharsets.UTF_8));
			os.write("<epicenterLon unit=\"DEGREE\">122.19</epicenterLon>".getBytes(StandardCharsets.UTF_8));
			os.write("<epicenterLat unit=\"DEGREE\">24.14</epicenterLat>".getBytes(StandardCharsets.UTF_8));
			os.write("</epicenter>".getBytes(StandardCharsets.UTF_8));
			os.write("<depth unit=\"KILOMETER\">13.8</depth>".getBytes(StandardCharsets.UTF_8));
			os.write("<magnitude>".getBytes(StandardCharsets.UTF_8));
			os.write("<magnitudeType>Mpd</magnitudeType>".getBytes(StandardCharsets.UTF_8));
			os.write("<magnitudeValue>6.8</magnitudeValue>".getBytes(StandardCharsets.UTF_8));
			os.write("</magnitude>".getBytes(StandardCharsets.UTF_8));
			os.write("</earthquake>".getBytes(StandardCharsets.UTF_8));
			os.close();
			sc.close();
		}
//		this.client();
	}
	public void client() throws IOException {
		Socket client = new Socket("localhost", 8888);
		InputStream in = client.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		try {
			for (String line; (line = br.readLine()) != null; ) {
				sb.append(line);
			}
		} catch (Exception e) {
			in.close();
		}
		System.out.println(sb);
		Document doc = convertStringToXMLDocument( sb.toString() );
		doc.getDocumentElement().normalize();
		String identifier = doc.getElementsByTagName("identifier").item(0).getTextContent();
		String event = doc.getElementsByTagName("event").item(0).getTextContent();
		String sentTime = doc.getElementsByTagName("sent").item(0).getTextContent();
		String description = doc.getElementsByTagName("description").item(0).getTextContent();
		String originTime = doc.getElementsByTagName("originTime").item(0).getTextContent();
		BigDecimal epicenterLon = new BigDecimal(doc.getElementsByTagName("epicenterLon").item(0).getTextContent());
		BigDecimal epicenterLat = new BigDecimal(doc.getElementsByTagName("epicenterLat").item(0).getTextContent());
		BigDecimal depth = new BigDecimal(doc.getElementsByTagName("depth").item(0).getTextContent());
		String magnitudeType = doc.getElementsByTagName("magnitudeType").item(0).getTextContent();
		BigDecimal magnitudeValue = new BigDecimal(doc.getElementsByTagName("magnitudeValue").item(0).getTextContent());

		EarthquakePK id = new EarthquakePK();
		id.setIdentifier(identifier);
		Earthquake entity = new Earthquake();
		entity.setId(id);
		entity.setEvent(event);
		entity.setSentTime(sentTime);
		entity.setDescription(description);
		entity.setOriginTime(originTime);
		entity.setEpicenterLon(epicenterLon);
		entity.setEpicenterLat(epicenterLat);
		entity.setDepth(depth);
		entity.setMagnitudeType(magnitudeType);
		entity.setMagnitudeValue(magnitudeValue);
		repo.save(entity);
		client.close();
	}
//	public void connectEarthquake() {
//		RestTemplate restTemplate = new RestTemplate();
//		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//		converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM}));
//		restTemplate.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
//		try {
//			HttpHeaders headers = createHttpHeaders("CO.TSMC_H", "1234abcd");
//			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//			interceptors.add(new LengthInterceptor());
//			restTemplate.setInterceptors( interceptors );
//			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//			logger.info("Result - status ( {} ) has body: {} ", response.getStatusCode(), response.hasBody());
//		} catch (Exception e) {
//			logger.error("ERROR: {}", e.getMessage());
//		}
//	}
//	private HttpHeaders createHttpHeaders(String user, String password) {
//		String notEncoded = user + ":" + password;
//		String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.add("Authorization", "Basic " + encodedAuth);
//		return headers;
//	}

//	private void getEarthQuakeData() {
//
//		HttpGet httpGet = new HttpGet(url);
//		try {
//			DefaultHttpClient httpClient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpClient.execute(httpGet);
//
//			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				ByteArrayDataSource source = new ByteArrayDataSource(httpResponse.getEntity().getContent(),
//						"multipart/mixed");
//				MimeMultipart multipart = new MimeMultipart(source);
//				logger.info("Result - status ( {} ) ", multipart);
//			} else {
//				HttpEntity httpDataEntity = httpResponse.getEntity();
//				EntityUtils.consumeQuietly(httpDataEntity);
//			}
//		} catch (Exception ex) {
//			logger.info("ERROR: {} ", ex);
//		}
//	}
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowCredentials(true);
//		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
//				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
//				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
//		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
//				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
//		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//		return new CorsFilter(urlBasedCorsConfigurationSource);
//	}
}
