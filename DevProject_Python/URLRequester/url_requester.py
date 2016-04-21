import urllib
import urllib2
import logging
import os
import xml.dom.minidom
import xml.etree.ElementTree as eTree

SOAP_URL = 'http://www.webservicex.net/geoipservice.asmx?WSDL'
REST_URL = 'http://jsonplaceholder.typicode.com'

class RequestSender(object):
    def __init__(self, file_name):
        self._fileName = file_name

    def soap_request_sender(self, action):
        body = ""
        with open(self._fileName) as dataFile:
            # get all lines, and pass it to a string
            body = "".join(dataFile.readlines())

        # print('REQUEST')
        # print(body)

        try:
            all_headers = {
                'Content-Type': 'text/xml',
                'Content-Length': len(body),
                'SOAPAction': action
            }
            r1 = urllib2.Request(url=SOAP_URL, headers=all_headers, data=body)

            # get repsonse object
            response = urllib2.urlopen(r1)

            # read all lines from response
            res_data = "".join(response.readlines())

            # parse response XML
            res_xml = xml.dom.minidom.parseString(res_data)

            # make xml pretties
            pretty_xml_as_string = res_xml.toprettyxml()

            # print('RESPONSE')
            # print(pretty_xml_as_string)

            # parsing the xml string
            # it retrieves an element tree
            etree = eTree.fromstring(res_data)
            # TODO: do some stuff with that data, but remember to use namespaces inside XML to access elements

            return pretty_xml_as_string

        except Exception as e:
            print(e)

    def request_sender(self, action):
        URL = "".join([REST_URL, action])
        try:
            # create a request object using URL
            req = urllib2.Request(URL)

            # get repsonse object
            response = urllib2.urlopen(req)

            # read all lines from response
            # res_data is a JSON, similar to dictionary in Python
            res_data = "".join(response.readlines())
            # print(res_data)

            # TODO: do some stuff with returned dictionary
            return res_data

        except Exception as e:
            print(e)

if __name__ == '__main__':
    send = RequestSender("data/soap_request.xml")
    print send.soap_request_sender('http://www.webservicex.net/GetGeoIP')
    print send.request_sender('/users')

