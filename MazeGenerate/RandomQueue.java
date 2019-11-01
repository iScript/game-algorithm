import java.util.ArrayList;

public class RandomQueue<E>{

    private ArrayList<E> queue;

    public RandomQueue(){
        queue = new ArrayList<E>();
    }

    public void add(E e){
        queue.add(e);
    }

    public E remove(){
        if(queue.size() == 0)
            throw new IllegalArgumentException("There's no element to remove in Random Queue");

        int randIndex = (int)(Math.random()*queue.size());

        E randElement = queue.get(randIndex);
        queue.set(randIndex, queue.get(queue.size()-1));    //将这个随机位置的值设置为queue的最后一个元素
        queue.remove(queue.size()-1);   // 移除最后一个元素

        return randElement; // 返回这个array的随机元素
    }

    public int size(){
        return queue.size();
    }

    public boolean empty(){
        return size() == 0;
    }
}