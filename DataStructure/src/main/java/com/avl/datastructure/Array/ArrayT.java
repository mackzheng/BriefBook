package com.avl.datastructure.Array;

/**
 * 扩展基本数组，泛型数组
 */
public class ArrayT<E> {

    private E[] data; //数组
    private int size;   //长度

    /**
     * 无参构造函数
     */
    public ArrayT() {
        this(5);
    }


    public ArrayT(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 获取数组大小
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     *
     * @return
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 判断数组是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 向数组尾部添加元素
     *
     * @param value
     */
    public void addLast(E value) {

//        if (size == data.length) throw new IllegalArgumentException("addLast failed,the array is full");
//        data[size] = value;
//        size++;

        //优化
        add(size, value);
    }

    /**
     * 向数组头节点添加元素
     *
     * @param value
     */
    public void addFirst(E value) {
        add(0, value);
    }

    /**
     * 根据索引，插入元素
     *
     * @param index
     * @param value
     */
    public void add(int index, E value) {
        if (size == data.length) throw new IllegalArgumentException("add failed,the array is full");

        if (index < 0 || index > size)
            throw new IllegalArgumentException("add failed, index>=0 && index>size");

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        data[index] = value;
        size++;
    }


    /**
     * 获取对应索引的元素
     *
     * @param index
     * @return
     */
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("add failed, index>=0 && index>size");
        return data[index];
    }

    /**
     * 修改对应索引的元素
     *
     * @param index
     * @param value
     */
    public void set(int index, E value) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("add failed, index>=0 && index>size");

        data[index] = value;
    }

    /**
     * 是否包含元素
     *
     * @param value
     * @return
     */
    public boolean contains(E value) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(value))
                return true;
        }
        return false;
    }

    /**
     * 查找元素
     *
     * @param value
     * @return
     */
    public int find(E value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value)
                return i;
        }
        return -1;
    }

    /**
     * 删除索引元素
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("add failed, index>=0 && index>size");

        E ret = data[index];

        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null; //释放空间，GC  loitering Objects
        return ret;
    }

    /**
     * 删除头元素
     *
     * @return
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 删除尾元素
     *
     * @return
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 删除元素
     *
     * @param value
     */
    public void removeElement(E value) {
        int index = find(value);
        if (index != -1) {
            remove(index);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("array:size=%d, capacity = %d\n", size, data.length));
        result.append("[");
        for (int i = 0; i < size; i++) {
            result.append(data[i]);
            if (i != size - 1)
                result.append(",");
        }
        result.append("}");
        return result.toString();
    }

}
