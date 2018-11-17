Array 数组
ArrayT 泛型数组
DynamicArray 动态数组

时间复杂度：
addLast(e)    O(1)
addFirst(e)   O(n)
add(index,e)  O(n/2) -> O(n)
resize        O(n)

removeLast(e)    O(1)
removeFirst(e)   O(n)
remove(index,e)  O(n/2)  -> O(n)

get(index)       O(1)
contains(e)      O(n)
find(e)          O(n/2) -> O(n)


增  O(n)
删  O(n)
改  O(1)~O(n)
查  O(1)~O(n)

均摊复杂度：
