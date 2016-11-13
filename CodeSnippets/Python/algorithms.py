__author__ = 'tahsin'

def string_permutation():
    print("===== string_permutation")
    def permute(lst, l, r):
        if l == r:
            print "".join(lst)
        else:
            for i in range(l, r):
                # swap right and left values
                lst[l], lst[i] = lst[i], lst[l]
                # for each char, we need keep one of them constant
                # print "".join(lst)
                permute(lst, l+1, r)
                # swap right and left values to keep the string same
                lst[l], lst[i] = lst[i], lst[l]

    word = "ABC"
    permute(list(word), 0, len(word))

def balanced_brackets():
    """
    Create function that will determine are the parenthesis balanced in a given string
    boolean isBalanced(string)

    a(bcd)d => true
    (kjds(hfkj)sdhf => false
    (sfdsf)(fsfsf => false
    :return:
    """
    print("===== balanced_brackets")
    def is_balanced(word):
        pushChars = "("
        popChars = ")"
        # check the numbers of the brackest are equal or not
        # if word.count('(', 0, -1) != word.count(')', 0, -1):
        #     print('number not equal!', word.count(')', 0, -1), word.count('(', 0, -1))
        #     return False

        brackets = []
        for ch in word:
            if ch in pushChars:
                brackets.append(ch)
            elif ch in popChars:
                # nothing to remove
                if not len(brackets):
                    return False
                idx = popChars.index(ch)
                popped = brackets.pop()
                #print "popped {0}, {1} ".format(popped, pushChars[idx])
                if popped != pushChars[idx]:
                    return False
        # if there is nothing to remove, it will return True
        return not len(brackets)

    exps = "()fsfsf()()()("
    print(is_balanced(exps))

def is_balanced_v2():
    # ([{}])

    def isbalanced(exp):
        pushChars = ['(', '[', '{']
        popChars = [')', ']', '}']

        all_chars = []
        for item in exp:
            if item in pushChars:
                all_chars.append(item)
            elif item in popChars:
                idx = popChars.index(item)
                if pushChars[idx] != all_chars.pop():
                    return False

        return len(all_chars) == 0

    print(isbalanced(raw_input()))

def fibonacci_question():
    print("===== fibonacci_question")
    def fibo(n):
        if n == 0:
            return 0
        if n == 1:
            return 1

        return fibo(n-1) + fibo(n-2)

    def fibo_iter(n):
        first, second = 1, 2
        if n in (0, 1):
            return first
        elif n == 2:
            return second
        else:
            for i in range(1,n):
                tmp = second
                second = first + second
                first = tmp
            return second

    for i in range(10):
        print(fibo_iter(i))

def fibonacci_question_better_performance():
    import sys
    calculated_vals = {}

    def fibo(n):
        global calculated_vals
        if n == 0:
            return 0
        if n == 1:
            return 1

        #if not (calculated_vals.get(n-1) and calculated_vals.get(n-2)):
        #    return calculated_vals[n-1] + calculated_vals[n-2]
        if calculated_vals.get(n) is not None:
            return calculated_vals[n]
        else:
            n_1 = fibo(n-1)
            n_2 = fibo(n-2)
            calculated_vals[n-1] = n_1
            calculated_vals[n-2] = n_2
            return n_1 + n_2

    solved = {}
    values = []
    #inp = raw_input()
    for inp in sys.stdin:
        val = int(inp)
        """
        if not solved.get(val):
            res = fibo(val)
            solved[val] = res
            values.append(res)
        else:
            values.append(solved.get(val))
        """

        values.append(fibo(val))
        #inp = raw_input()

    for item in values:
        print(item)

def hotel_ranking():
    """
    You have rating (0-10) of the hotels per user in this format:

    scores = [
        {'hotel_id': 1001, 'user_id': 501, 'score': 7},
        {'hotel_id': 1001, 'user_id': 502, 'score': 7},
        {'hotel_id': 1001, 'user_id': 503, 'score': 7},
        {'hotel_id': 2001, 'user_id': 504, 'score': 10},
        {'hotel_id': 3001, 'user_id': 505, 'score': 5},
        {'hotel_id': 2001, 'user_id': 506, 'score': 5}
    ]

    Any given hotel might have more than one score.

    Implement a function, get_hotels(scores, min_avg_score) that returns a list of hotel ids
    that have average score equal to or higher than min_avg_score.
    :return:
    """
    print("===== hotel_ranking")
    scores = [
        {'hotel_id': 1001, 'user_id': 501, 'score': 7},
        {'hotel_id': 1001, 'user_id': 502, 'score': 7},
        {'hotel_id': 1001, 'user_id': 503, 'score': 7},
        {'hotel_id': 2001, 'user_id': 504, 'score': 10},
        {'hotel_id': 3001, 'user_id': 505, 'score': 5},
        {'hotel_id': 2001, 'user_id': 506, 'score': 5}
    ]

    def get_hotels(scores, min_ranking):
        hotel_ids = []
        for item in scores:
            if item['score'] >= min_ranking and item['hotel_id'] not in hotel_ids:
                hotel_ids.append(item['hotel_id'])
        return hotel_ids

    print(get_hotels(scores, 6))

def find_in_two_arrays():
    """
    Given any number of arrays containing numbers, write a function which
    finds the numbers that appear in exactly two arrays.

    arrays = [
        [6, 2, 2, 0, 4],
        [5, 0, 2, 6, 7, 1],
        [6, 7, 9, 9],
    ]
    :return:
    """
    print("===== find_in_two_arrays")

    def find_in_two(lst):
        lst = [set(x) for x in lst]
        all = []
        for i in range(0, len(lst)-1):
            for j in range(i+1, len(lst)):
                all.extend(lst[i] & lst[j])
        return set(all)

    arrays = [
        [6, 2, 2, 0, 4],
        [5, 0, 2, 6, 7, 1],
        [6, 7, 9, 9],
    ]
    print(find_in_two(arrays))

# TODO:
"""
We have a digested server log with username, visited page and timestamp.
Create a processing algorithm that will output the most visited page/areas in such a way that will match partial path as well.
i.e.
    logs = [
        {'user': "user1", 'page':"/home" },
        {'user': "user1", 'page':"/home/account"},
        {'user': "user1", 'page':"/home/account/profile"},
        {'user': "user1", 'page':"/home/account/login"},
        {'user': "user2", 'page':"/about"},
        {'user': "user2", 'page':"/about/contact"},
        {'user': "user2", 'page':"/home"}
    ]

the output
user1
     - home
     - home/account
user2
     - /about
     - /home

"""
def most_visited():

    def find_most_visited(data):
        visits = {}
        for item in data:
            usr = item['user']
            page = item['page']
            # set ranking
            # item['rank'] = 0
            if usr not in visits.iterkeys():
                visits[usr] = [page]
            else:
                sb = [page[:len(x)] for x in visits[usr] if page > x and page[:len(x)] == x]
                if len(sb):
                    # visits[usr].append(sb[0])
                    visits[usr].extend(sb)
                else:
                    visits[usr].append(page)
        return visits

    def find_most_visited_v2(data):
        visits = {}
        idx = 0
        for item in data:
            usr = item['user']
            page = item['page']
            # set ranking
            item['rank'] = 0
            for i in xrange(len(data)):
                if usr == data[i]['user']:
                    if page in data[i]['page']:
                        item['rank'] += 1

            idx += 1
        return sorted(data)

    logs = [
        # {'user': "user1", 'page':"/home/account"},
        {'user': "user1", 'page':"/home" },
        {'user': "user1", 'page':"/home/account"},
        {'user': "user1", 'page':"/home/account/profile"},
        {'user': "user1", 'page':"/home/account/login"},
        {'user': "user2", 'page':"/about"},
        {'user': "user2", 'page':"/about/contact"},
        {'user': "user2", 'page':"/home"}
    ]
    # print(find_most_visited(logs))
    # print(find_most_visited_v2(logs))
    for item in find_most_visited_v2(logs):
        print(item)

def array_shift():
    """
        shift an array to the right by n positions such that
        the right most indexes are become the first ones and the first ones move ahead.
        like:
        1 2 3 4 5 --> shift by 2 --> 4 5 1 2 3
    """
    print("===== array_shift")
    def shifter(arr, n):
        # get the repeating shift if exists
        n = n % len(arr)

        for i in xrange(n):
            arr.insert(0, arr.pop())
        return arr

    array = [1, 2, 3, 4, 5]
    # print(shift(array, 2))
    print(shifter(array, 3))

def coin_change_problem():
    print("===== coin_change_problem")
    target = 5
    coins = [1, 2, 3]
    ways = [1]+[0]*target
    for coin in coins:
        for i in range(coin, target+1):
            print('for coin {0} checking coin {1}'.format(coin, i))
            ways[i] += ways[i-coin]
            print(ways[i])
    print ways[target]
    print(ways)

def robot():
    """

    // We have a robot on a 2-dimensional grid, which can move vertically or horizontally. It starts at (0, 0) coordinates and accepts a string with sequence of commands.
    // Each character represents a command:
    // '>' - move East
    // '<' - move West
    // '^' - move North
    // 'v' - move South

    // Given a command string, please write a function to detect if the robot trajectory has a loop.

    // For example:
    // >>^>>^<<vvv<<^<vv>>
    ///   ^     ^
    // has one because of >>^<<v part

    :return:
    """
    def robot_v1():
        command = ">>^>>^<<vvv<<^<vv>>"
        path = []
        x, y = 0,0
        path.append({'x': x, 'y':y})
        for ch in command:
            if ch == '>':
                x += 1
            elif ch == '<':
                x -= 1
            elif ch == '^':
                y += 1
            elif ch == 'v':
                y -= 1

            if path:
                if [cc for cc in path if cc['x'] == x and cc['y'] == y]:
                    print('I was here ', x, y)

            path.append({'x': x, 'y':y})

    def robot_v2():
        command = ">>^>>^<<vvv<<^<vv>>"
        x, y = 0,0
        coordinates = {}
        coordinates[str(x) + str(y)] = 1
        for ch in command:
            if ch == '>':
                x += 1
            elif ch == '<':
                x -= 1
            elif ch == '^':
                y += 1
            elif ch == 'v':
                y -= 1

            hashcode = str(x) + str(y)
            print('Now I am going to {0},{1}'.format( x, y))
            if coordinates.get(hashcode) is None:
                coordinates[str(x) + str(y)] = 1
            else:
                print('I was here ', list(hashcode))

    robot_v1()
    robot_v2()

def binary_search_tree():
    print("===== binary_search_tree")

    class Node:
        def __init__(self, number):
            self.data = number
            self.left = None
            self.right = None

        def insert(self, number):
            if number <= self.data:
                if self.left is None:
                    self.left = Node(number)
                else:
                    self.left.insert(number)
            else:
                if self.right is None:
                    self.right = Node(number)
                else:
                    self.right.insert(number)

        def printInOrder(self):
            if self.left is not None:
                self.left.printInOrder()
            print(self.data)
            if self.right is not None:
                self.right.printInOrder()

        def produce_inorder_list(self, lst):
            if self.left is not None:
                self.left.produce_inorder_list(lst)
            # print(self.data)
            lst.append(self.data)
            if self.right is not None:
                self.right.produce_inorder_list(lst)

        def produce_list(self):
            lst = []
            self.produce_inorder_list(lst)
            return lst

    class BinarySearchTree:
        def __init__(self, node=None):
            # if node is not None:
            #     self.node = node
            #
            self.node = node

        def insert(self, number):
            if self.node is None:
                self.node = Node(number)
                return

            self.node.insert(number)

        def printInOrder(self):
            self.node.printInOrder()

        def print_by_level(self):
            queue = []
            queue.append(self.node)

            while len(queue) > 0:
                n = queue[0]
                print(n.data)
                queue.pop(0)

                if n.left is not None:
                    queue.append(n.left)

                if n.right is not None:
                    queue.append(n.right)


        @staticmethod
        def sort_list(lst):
            tmp_node = Node(lst[0])
            for item in lst[1:]:
                tmp_node.insert(item)

            return tmp_node.produce_list()


    tree = BinarySearchTree(Node(4343))
    tree.insert(42)
    tree.insert(4)
    tree.insert(342)
    tree.insert(4572)
    tree.insert(1)

    tree.printInOrder()
    print('now printing by level')
    tree.print_by_level()
    print(BinarySearchTree.sort_list([24, 52, 1, 8, 374, 4]))

string_permutation()
balanced_brackets()
fibonacci_question()
hotel_ranking()
find_in_two_arrays()
array_shift()
coin_change_problem()
most_visited()
robot()
binary_search_tree()


