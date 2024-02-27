package org.ClientModel;

public enum test {
    test1(0),
    test2(1),
    test3(2),
    test4(3);
    private int val;
    private test(int titem)
    {
      this.val = titem;
    }
    public int getattr()
    {
        return this.val;
    }
    public void setenum(int val)
    {
        this.val = val;
    }

}

