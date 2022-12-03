public class stackClass<E> implements stack<E> {
    private E[] data;
    int top = -1;
    public stackClass()
    {
        data =  (E[]) new Object[1000];
    }
    @Override
    public void push(E e)
    {
        data[top+1] = e;
        top = top + 1;
    }
    @Override
    public E pop() {
        E answer =  data[top];
        data[top] = null;
        top --;
        return answer;
    }
    @Override
    public E peek() {
        return data[top];
    }
    @Override
    public int size() {
        return (top + 1);
    }
    @Override
    public boolean isEmpty() {
        return (top == -1);
    }
}
