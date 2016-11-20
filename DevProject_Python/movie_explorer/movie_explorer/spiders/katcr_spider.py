import scrapy

from movie_explorer.items import TorrentItem
from urlparse import urljoin

"""

 A movie torrent finder by getting data from https://kat.cr
 
 "scrapy crawl katcr -o testdata.json > out.txt" can be used inside project folder to run the application.
 Run the command where items.py, settings.py etc exist.

 Then, run main.py to generate JSON files.
 
"""
class KatcrSpider(scrapy.Spider):
    name = "katcr"
    allowed_domains = ["kat.cr"]
    start_urls = [
        "https://kat.cr/movies/"
    ]

    BASE_URL = 'https://kat.cr/movies/'

    def parse(self, response):
         pages = response.xpath('///div[@class="pages botmarg5px floatright"]/a/@href')
         item = TorrentItem()

         # get last page info
         last_page = response.xpath('//div[@class="pages botmarg5px floatright"]/a[position() = last() ]/text()').extract_first()
         print(last_page)
         last_page = int(last_page)

         base_url = 'https://kat.cr/movies/'
         for idx in range(0, last_page):
             # url = base_url + str(idx)
             url = response.urljoin(base_url + str(idx))

             request = scrapy.Request(url, callback=self.parse_dir_contents)
             request.meta['item'] = item
             yield request


    def parse_dir_contents(self, response):
        # main torrent page i.e. //movie/1
        print('=================== PAGES PAGE : ' + response.url)
        # get movie nodes
        # each page line inside torrents whole page
        sels = response.xpath('///div[@class="markeredBlock torType filmType"]')

        print 'Selections: ', sels
        for sel in sels:
            item = TorrentItem()
            # get the first link on the selected page
            link = sel.xpath('a/@href').extract_first()
            name = sel.xpath('a/text()').extract_first()
            pages_link = 'https://kat.cr' + link

            # seed = link
            # item['pages_link'] = 'https://kat.cr' + link
            # item['torrent_name'] = name
            # item['parent_link'] = link
            url = response.urljoin(pages_link)
            request = scrapy.Request(url, callback=self.get_movie_info)

            # get movie information from the link page
            yield request

    def get_movie_info(self, response):
        # one page inside a torrent main page (//movie/1)
        # kat.cr/abcd.html
        print("================ CURRENT PAGE ", response.url)

        item = TorrentItem()

        # torrent name
        name = response.xpath('///div[@class="markeredBlock torType filmType"]/a/text()').extract_first()
        item['torrent_name'] = name.encode('utf8')

        #response.url is the page url
        item['pages_link'] = response.url

        # get the first link class="novertmarg"
        link = response.xpath('///div[@class="torrentMediaInfo"]/a/@href').extract_first()

        # parent link for that movie, includes other similar movies
        item['parent_link'] = urljoin(self.BASE_URL, link)

        # imdb_rating = self.get_imdb_rating(response)
        imdb_rating = self.get_imdb_ratio(response)

        # calculated imdb rating, rate * votes
        # print('========== RATING ' + self.get_imdb_rating(response))
        item['imdb_rating'] = imdb_rating

        # size information
        sizeAmt = response.xpath('//td[@class="torFileSize"]/text()').extract_first()
        sizeType = response.xpath('//td[@class="torFileSize"]/span/text()').extract_first()
        size = sizeAmt + sizeType
        item['size'] = size

        print('================ GET MOVIE ITEM : ', item)
        return item


    def get_imdb_rating(self, response):
        imdb_rating = ''
        all_li = response.xpath('///div[@class="dataList"]/ul/li')
        for li in all_li:
            tmp = li.xpath('strong[text()="IMDb rating:"]')
            if tmp:
                imdb_rating = li.xpath('text()').extract_first()
                break

        return imdb_rating

    def get_imdb_ratio(self, response):
        imdb_rating = str(self.get_imdb_rating(response)).encode(encoding='ascii')

        # remove whitespaces
        imdb_rating = imdb_rating.replace(' ', '')

        # remove brackets
        imdb_rating = imdb_rating.replace('votes)', '')

        score, votes = imdb_rating.split('(')

        # remove fractions to have the correct number
        votes = votes.replace(',', '')
        score = score.replace('.', '')

        score = score.encode(encoding='utf8')
        votes = votes.encode(encoding='utf8')

        # return calculated ratio
        return int(score) * int(votes)
