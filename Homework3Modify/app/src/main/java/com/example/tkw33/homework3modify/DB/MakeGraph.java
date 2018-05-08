package com.example.tkw33.homework3modify.DB;

import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;
import com.example.tkw33.homework3modify.GeoJson.NodeLatLng;

import java.util.ArrayList;

public class MakeGraph {
    int nodeSize, linkSize;
    double mSLat, mSLng, mELat, mELng;
    double tmp;
    double nodeLat, nodeLng, linkTailLat, linkTailLng, linkHeadLat, linkHeadLng;
    NodeLatLng nodeLatLng = new NodeLatLng();
    //LinkLatLng linkLatLng;
    //ArrayList<LinkLatLng> mList;
    ArrayList<NodeLatLng> mNodeList;
    //ArrayList<NodeLatLng> mReturnList;
    ArrayList<LinkLatLng> mLinkList;
    int index = 0;
    public MakeGraph(double mSLat, double mSLng, double mELat, double mELng){
        mNodeList = new ArrayList<>();

        //mReturnList = new ArrayList<>();
        if( mELat < mSLat ){
            tmp = mELat;
            mELat = mSLat;
            mSLat = tmp;
        }
        if( mELng < mSLng ) {
            tmp = mELng;
            mELng = mSLng;
            mSLng = tmp;
        }
        this.mSLat = mSLat;
        this.mSLng = mSLng;
        this.mELat = mELat;
        this.mELng = mELng;

        for(int i = 0; i < NodeLatLng.nlist.size() - 1; i++){
            if ( ((this.mSLat - 0.0015 <= NodeLatLng.nlist.get(i).lat) &&
                    (NodeLatLng.nlist.get(i).lat <= this.mELat + 0.0015)) &&
                    ((this.mSLng - 0.0015 <= NodeLatLng.nlist.get(i).lng) &&
                            NodeLatLng.nlist.get(i).lng <= this.mELng + 0.0015) ) {
                mNodeList.add(NodeLatLng.nlist.get(i));
            }
        }
        //init();
    }
    public ArrayList<NodeLatLng> init(){
        int k = 0;
        LinkLatLng linkLatLng;
        ArrayList<NodeLatLng> returnArray = new ArrayList<>();
        //mLinkList = new ArrayList<>();
        //LinkLatLng[] linkLatLngs = new LinkLatLng[10000];
        /*for(int i = 0; i < mNodeList.size() - 1; i++) {
            //mList = new ArrayList<>();
            mLinkList = new ArrayList<>();
            nodeLat = mNodeList.get(i).lat;
            nodeLng = mNodeList.get(i).lng;
            nodeLatLng = mNodeList.get(i);*/
            //nodeLat = NodeLatLng.nlist.get(i).lat;
            //nodeLng = NodeLatLng.nlist.get(i).lng;
        mLinkList = new ArrayList<>();
            for(int i = 0; i < LinkLatLng.llist.size() - 1; i++){
                ///mLinkList = new ArrayList<>();
                linkTailLat = LinkLatLng.llist.get(i).coordinate.get(0).latitude;    //index 0이 tail이 맞는지.
                linkTailLng = LinkLatLng.llist.get(i).coordinate.get(0).longitude;
                linkHeadLat = LinkLatLng.llist.get(i).coordinate.get(LinkLatLng.llist.get(i).coordinate.size() - 1).latitude;
                linkHeadLng = LinkLatLng.llist.get(i).coordinate.get(LinkLatLng.llist.get(i).coordinate.size() - 1).longitude;
                linkLatLng = new LinkLatLng(LinkLatLng.llist.get(i).link_id, LinkLatLng.llist.get(i).coordinate
                        , LinkLatLng.llist.get(i).distance);
                for(int j = 0; j < mNodeList.size() ; j++) {
                    //mList = new ArrayList<>();
                    nodeLat = mNodeList.get(j).lat;
                    nodeLng = mNodeList.get(j).lng;
                    nodeLatLng = mNodeList.get(j);

                    //범위 수정하고 a*알고리즘 수행할수있도록.
                    if (((nodeLat - 0.0005 <= linkTailLat && linkTailLat <= nodeLat + 0.0005) &&
                            (nodeLng - 0.0005 <= linkTailLng && linkTailLng <= nodeLng + 0.0005))) {  //또는 nodeLat <= linkTailLat <= nodeLat+0.0001 같은 방식으로
                        //LinkLatLng.llist.get(i).setNode(nodeLatLng);
                        //mLinkList.add(LinkLatLng.llist.get(i));
                        //linkLatLng = new LinkLatLng(LinkLatLng.llist.get(i).link_id, LinkLatLng.llist.get(i).coordinate
                        //        , LinkLatLng.llist.get(i).distance);
                        linkLatLng.setNode(mNodeList.get(j));
                        //mLinkList.get(j).setNode(linkLatLng.getNode());
                        //mLinkList.add(linkLatLng); //node 들어가는지 확인
                        //if(mLinkList.get(k).link_id == LinkLatLng.llist.get(i).link_id)
                          //  mLinkList.get(k).setNode(mNodeList.get(j));

                    }
                    if (((nodeLat - 0.0005 <= linkHeadLat && linkHeadLat <= nodeLat + 0.0005) &&
                            (nodeLng - 0.0005 <= linkHeadLng && linkHeadLng <= nodeLng + 0.0005))) {
                        //LinkLatLng.llist.get(i).setNextNode(nodeLatLng);
                        //mLinkList.add(LinkLatLng.llist.get(i));
                        //linkLatLng = new LinkLatLng(LinkLatLng.llist.get(i).link_id, LinkLatLng.llist.get(i).coordinate
                        //        , LinkLatLng.llist.get(i).distance);
                        linkLatLng.setNextNode(mNodeList.get(j));
                        //mLinkList.add(linkLatLng);
                        //if(mLinkList.get(k).link_id == LinkLatLng.llist.get(i).link_id)
                          //  mLinkList.get(k).setNextNode(mNodeList.get(j));
                    }
                    if (((linkLatLng.getNode() != null) && (linkLatLng.getNextNode() != null))
                    && (linkLatLng.getNode().node_id != linkLatLng.getNextNode().node_id)) {
                        //&& (mLinkList.get(k).node != null && mLinkList.get(k+1).nextNode != null)
                        //&& (mLinkList.get(k).getNode().node_id != mLinkList.get(k+1).getNextNode().node_id)) {
                        mLinkList.add(linkLatLng);
                        mNodeList.get(j).setLink(mLinkList);
                        returnArray.add(mNodeList.get(j));
                        ///mLinkList = new ArrayList<>();
                        linkLatLng = new LinkLatLng(LinkLatLng.llist.get(i).link_id, LinkLatLng.llist.get(i).coordinate
                                , LinkLatLng.llist.get(i).distance);
                        //k += 2;
                    }
                    //linkLatLng = new LinkLatLng(LinkLatLng.llist.get(j).link_id, LinkLatLng.llist.get(j).coordinate
                    //, LinkLatLng.llist.get(j).distance);
                }
                //mLinkList.add(linkLatLng);
            //mLinkList.get().setNode();
                //if(mLinkList.size() > 0 && k < mNodeList.size()) {
                  //  mNodeList.get(k++).setLink(mLinkList);    //node와 nextNode 다 들어가는지 확인 //mLinkList 비어있음.
                //}
            //mNodeList.get(i).setLink(mLinkList);
            //NodeLatLng.nlist.get(i).setLink(mList);
        }
        return returnArray;
        ///return mLinkList;
    }

}
