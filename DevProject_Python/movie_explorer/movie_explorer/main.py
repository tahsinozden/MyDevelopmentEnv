__author__ = 'tahsin'

import json
import operator
from pprint import pprint

"""
	Main script for sorting information from downloaded data from website.
	After generating JSON files, under the directory which index.html exists,
	run the following command to start a localserver "python -m SimpleHTTPServer".
	local server will be available at http://http://localhost:8000/
"""

def sort_list(lst):
    tmp = lst[0]
    i = 0
    j = 0
    while i < len(lst)-1:
        j = i + 1
        while j < len(lst):
            obj = lst[j]
            if [lst[j]['imdb_rating'] < lst[i]['imdb_rating']]:
                tmp = lst[i]
                lst[i] = lst[j]
                lst[j] = tmp
            j += 1
        i += 1

    return lst

def find_min(lst):
    min = lst[0]
    idx = 0
    minIdx = 0

    for item in lst:
        if item['imdb_rating'] < min['imdb_rating']:
            min = item
            minIdx = idx

        idx += 1

    return minIdx

def find_max(lst):
    max = lst[0]
    idx = 0
    mmaxIdx = 0

    for item in lst:
        if item['imdb_rating'] > max['imdb_rating']:
            max = item
            mmaxIdx = idx

        idx += 1

    return mmaxIdx

def get_sorted_list(lst):
    tmpLst = []

    while len(lst) > 0:
        idx = find_max(lst)
        tmpLst.append(lst[idx])
        lst.pop(idx)

    return tmpLst

def createreport():
    with open('testdata.json') as data_file:
        data = json.load(data_file)


    # lst = sort_list(data)
    lst = get_sorted_list(data)
    # pprint(lst)


    with open('testreport.json', 'w') as out:
        # for item in lst:
        #     out.write(str(item) + "\n")
        out.write(json.dumps(lst, sort_keys=True,
                      indent=4, separators=(',', ': ')))

    unique_lst = create_unique_records(lst)
    # write summary with unique records
    with open('test-unique-report.json', 'w') as out:
        # for item in unique_lst:
        #     out.write(str(item) + "\n")
        out.write(json.dumps(unique_lst, sort_keys=True,
                      indent=4, separators=(',', ': ')))

def choose_uniquerecord(seq):
    seen = set()
    seen_add = seen.add
    return [x for x in seq if not (x in seen or seen_add(x))]

def create_unique_records(lst):
    unique_list = []
    for item in lst:
        if(not checkdict_inside_list(item, unique_list)):
            unique_list.append(item)
    return unique_list

def checkdict_inside_list(dictObj, lst):
    for item in lst:
        if (dictObj['parent_link'] == item['parent_link']):
            # inside
            return True
    # not inside
    return False

if __name__ == '__main__':
    createreport()