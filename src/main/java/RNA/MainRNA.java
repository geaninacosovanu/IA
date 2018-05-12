package RNA;

import Utils.ReadData;

public class MainRNA {
    public static void main(String[] args) {

//        Service service=new Service();
//        service.train(ReadData.readX(), ReadData.readY(),0.001f,3,10);
        TestAccuracyRNA test =new TestAccuracyRNA(ReadData.readX(),ReadData.readY());
        Double accuracy=test.test();
        System.out.println(accuracy);

    }
}
