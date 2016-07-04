# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class TorrentItem(scrapy.Item):
    torrent_name = scrapy.Field()
    parent_link = scrapy.Field()
    pages_link = scrapy.Field()
    imdb_rating = scrapy.Field()
    size = scrapy.Field()
    seed = scrapy.Field()

