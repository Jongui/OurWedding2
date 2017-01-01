package joaogd53.com.br.ourweddingapp.application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import joaogd53.com.br.ourweddingapp.R;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.ourweddingapp.model.HoneyMoonGift;
import joaogd53.com.br.ourweddingapp.model.Quota;
import joaogd53.com.br.ourweddingapp.model.Store;
import joaogd53.com.br.ourweddingapp.model.Story;
import joaogd53.com.br.ourweddingapp.model.StoryComment;
import joaogd53.com.br.ourweddingapp.thread.GetAllGuestsRunnable;
import joaogd53.com.br.ourweddingapp.thread.GetAllHoneyMoonGiftsRunnable;
import joaogd53.com.br.ourweddingapp.thread.GetAllStoriesRunnable;
import joaogd53.com.br.ourweddingapp.thread.GetLastStoriesRunnable;
import joaogd53.com.br.ourweddingapp.thread.GetStoryCommentsRunnable;
import joaogd53.com.br.ourweddingapp.thread.GuestByIdRunnable;
import joaogd53.com.br.ourweddingapp.thread.GuestsForQrCodeRunnable;
import joaogd53.com.br.ourweddingapp.thread.LoginRunnable;
import joaogd53.com.br.ourweddingapp.thread.QuotaSaveRunnable;
import joaogd53.com.br.ourweddingapp.thread.SaveCommentRunnable;
import joaogd53.com.br.ourweddingapp.thread.UpdateGuestsRunnable;
import joaogd53.com.br.ourweddingapp.thread.UpdateUserRunnable;

public class OurWeddingApp {
    private static OurWeddingApp application;
    private Guest user;
    private List<Store> stores;
    private Context context;
    private String tokenId;
    private ProgressDialog mProgressDialog;

    private OurWeddingApp() {
        user = Guest.GuestBuilder.buildGuest();
    }

    public static OurWeddingApp getInstance() {
        if (application == null) {
            application = new OurWeddingApp();
        }
        return application;
    }

    public Guest getUser() {
        return this.user;
    }

    public void setUser(Guest user) {
        this.user = user;
    }

    public void setContext(Context context) {
        this.context = context;
        mProgressDialog = new ProgressDialog(this.context);
        mProgressDialog.setMessage(this.context.getResources().getString(R.string.loading));
        mProgressDialog.setIndeterminate(true);
    }

    public String getTokenId() {
        return this.tokenId;
    }

    public int logInUser(String tokenId, String personEmail) {
        user.setPersonEmail(personEmail);
        this.tokenId = tokenId;
        LoginRunnable login = new LoginRunnable((Activity) context);
        Thread t = new Thread(login);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        this.user = Guest.GuestBuilder.buildUser(personId, personName, personEmail, personPhoto);
        return login.getReturnCode();
    }

    public List<HoneyMoonGift> getHoneyMoonGifts() {
//        if (mProgressDialog != null) {
//            mProgressDialog.show();
//        }
        GetAllHoneyMoonGiftsRunnable runnable = new GetAllHoneyMoonGiftsRunnable((Activity) this.context);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
        HashMap<Integer, HoneyMoonGift> hashMap = HoneyMoonGift.getAllInstances();
        return new ArrayList<>(hashMap.values());
    }

    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Store store = Store.StoreBuilder.buildStore();
                store.setIdStore(i);
                switch (i) {
                    case 0:
                        store.setImgLarge(R.drawable.img_camicado_large);
                        store.setImgSmall(R.drawable.img_camicado_small);
                        store.setPhone("0800-722-0035");
                        store.setUrl("http://www.camicado.com.br/weddinglist/products/60321511");
                        store.setName("Camicado");
                        break;
                    case 1:
                        store.setImgLarge(R.drawable.img_havan_large);
                        store.setImgSmall(R.drawable.img_havan_small);
                        store.setPhone("(47)3251-5249");
                        store.setUrl("http://lista.havan.com.br/Convidado/ItensLista/1/570472");
                        store.setName("Havan");
                        break;
                    case 2:
                        store.setImgLarge(R.drawable.img_fastshop_large);
                        store.setImgSmall(R.drawable.img_fastshop_small);
                        store.setPhone("(41)2103-9200");
                        store.setUrl("http://listadecasamento.fastshop.com.br/ListaCasamento/ListaCasamentoProdutos.aspx?ID=220823");
                        store.setName("FastShop");
                        break;
                }
                stores.add(store);
            }
        }
        return stores;
    }

    public List<Guest> getGuests() {
//        if (mProgressDialog != null) {
//            mProgressDialog.show();
//        }
        GetAllGuestsRunnable runnable = new GetAllGuestsRunnable((Activity) this.context);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
        HashMap<Integer, Guest> hashMap = Guest.getAllInstances();
        return new ArrayList<>(hashMap.values());
    }

    public void updateUser(Guest guest) {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        UpdateUserRunnable runnable = new UpdateUserRunnable((Activity) this.context, guest);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public List<Guest> findInvitation(String qrCode) {
//        if (mProgressDialog != null) {
//            mProgressDialog.show();
//        }
        GuestsForQrCodeRunnable runnable = new GuestsForQrCodeRunnable((Activity) this.context, qrCode);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
        return runnable.getGuestList();
    }

    public HoneyMoonGift findHoneyMoonGift(int idHoneyMoonGift) {
        return HoneyMoonGift.HoneyMoonGiftBuilder.buildFromId(idHoneyMoonGift);
    }

    public int buyQuota(HoneyMoonGift gift, Double value, Date date) {
        Quota quota = Quota.QuotaBuilder.buildQuota(gift, value, date);
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        QuotaSaveRunnable runnable = new QuotaSaveRunnable(this.context, quota);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        return runnable.getReturnCode();
    }

//    public Story lastStory() {
//        GetLastStoriesRunnable runnable = new GetLastStoriesRunnable((Activity) this.context);
//        Thread t = new Thread(runnable);
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        return Story.getLastStory();
//    }

    public Guest getGuestById(int idGuest) {
        GuestByIdRunnable runnable = new GuestByIdRunnable((Activity) this.context, idGuest);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return runnable.getGuest();
    }

    public List<StoryComment> getStoryComments(Story story) {
        GetStoryCommentsRunnable runnable = new GetStoryCommentsRunnable((Activity) this.context, story);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int saveComment(StoryComment comment) {
        SaveCommentRunnable runnable = new SaveCommentRunnable((Activity) this.context, comment);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return runnable.getReturnCode();
    }

    public List<Story> getStories() {
        GetAllStoriesRunnable runnable = new GetAllStoriesRunnable((Activity) this.context);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Story> hashMap = Story.getAllInstance();
        return new ArrayList<>(hashMap.values());
    }

    public void updateGuests(List<Guest> guests) {
        UpdateGuestsRunnable runnable = new UpdateGuestsRunnable((Activity) this.context, guests);
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
