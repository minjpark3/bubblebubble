package bubble.test.ex01;

/**
 * 다중 상속이 안된느 것이 많기 때문에
 * default 사용하면 인터페이스도 몸체가 있는 매서드를 만들수 있다.
 * 
 */
public interface Moveable {
	
	public abstract void left();
	public abstract void right();
	public abstract void up();
	default public void down() {}; 
	default public void attack() {};
}
