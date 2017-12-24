package com.jonbore.utils.groovy

import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import org.apache.log4j.Logger

class GroovyUtils {

    private static Logger logger = Logger.getLogger(GroovyUtils.class);

    static void main(String[] args) {
//        buildXml()
        parseXml()
//        streamBuildXml()
    }

    static void parseXml() {
        def parse = new XmlParser()
        def doc = parse.parse("e:/test1.xml")

        doc.attributes().each {
            k, v ->
                println k + ":" + v
        }
        println '======================================'
        doc.movie.each {
            movie ->
                movie.attributes().each {
                    k, v ->
                        println k + ":" + v
                }
        }
        println '======================================'
        doc.movie.each {
            movie ->
                println 'movie name :' + movie['@title']
                println 'movie type :' + movie.type[0].text()
                println 'movie year :' + movie.year[0].text()
                println 'movie stars :' + movie.stars[0].text()
        }
    }

    static void buildXml() {
        def mp = [1: ['Enemy Behind', 'War, Thriller', 'DVD', '2003',
                      'PG', '10', 'Talk about a US-Japan war'],
                  2: ['Transformers', 'Anime, Science Fiction', 'DVD', '1989',
                      'R', '8', 'A scientific fiction'],
                  3: ['Trigun', 'Anime, Action', 'DVD', '1986',
                      'PG', '10', 'Vash the Stam pede'],
                  4: ['Ishtar', 'Comedy', 'VHS', '1987', 'PG',
                      '2', 'Viewable boredom ']]

        def strWrite = new StringWriter()
        def mB = new MarkupBuilder(strWrite)

        // Compose the builder
        def MOVIEDB = mB.collection('shelf': 'New Arrivals') {
            mp.each {
                sd ->
                    mB.bo(title: sd.value[0]) {
                        type(sd.value[1])
                        format(sd.value[2])
                        year(sd.value[3])
                        rating(sd.value[4])
                        stars(sd.value[4])
                        description("value": "zhoubo", sd.value[5])
                    }
            }
        }

        println(strWrite)

        def file = "e:/test1.xml"
        def fileWrite = new FileWriter(file)
        fileWrite.write(strWrite.toString())
        fileWrite.flush()
        fileWrite.close()
    }

    static void streamBuildXml() {
        def comment = "<![CDATA[<!-- address is new to this release -->]]>"
        def builder = new StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        def person = {
            mkp.xmlDeclaration()
            mkp.pi("xml-stylesheet": "type='text/xsl' href='myfile.xslt'")
            mkp.declareNamespace('': 'http://myDefaultNamespace')
            mkp.declareNamespace('location': 'http://someOtherNamespace')
            person(id: 100) {
                firstname("Jane")
                lastname("Doe")
                mkp.yieldUnescaped(comment)
                location.address("123 Main")
            }
        }
        def writer = new FileWriter("e:/person.xml")
        writer << builder.bind(person)
    }
}