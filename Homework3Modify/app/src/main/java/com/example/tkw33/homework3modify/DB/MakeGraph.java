package com.example.tkw33.homework3modify.DB;

import com.example.tkw33.homework3modify.GeoJson.LatLng;
import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;
import com.example.tkw33.homework3modify.GeoJson.NodeLatLng;

import org.w3c.dom.Node;

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
        else if( mELng < mSLng ) {
            tmp = mELng;
            mELng = mSLng;
            mSLng = tmp;
        }
        this.mSLat = mSLat;
        this.mSLng = mSLng;
        this.mELat = mELat;
        this.mELng = mELng;

        for(int i = 0; i < NodeLatLng.nlist.size() - 1; i++){
            if ( ((this.mSLat - 0.0005 <= NodeLatLng.nlist.get(i).lat) &&
                    (NodeLatLng.nlist.get(i).lat <= this.mELat + 0.0005)) &&
                    ((this.mSLng - 0.0005 <= NodeLatLng.nlist.get(i).lng) &&
                            NodeLatLng.nlist.get(i).lng <= this.mELng + 0.0005) ) {
                mNodeList.add(NodeLatLng.nlist.get(i));
            }
        }
        //init();
    }
    public ArrayList<NodeLatLng> init(){
        //nodeSize = NodeLatLng.nlist.size();
        //linkSize = LinkLatLng.llist.size();
        int linkSize = 0;
        //for(int i = 0; i < NodeLatLng.nlist.size() - 1; i++){
        //LinkLatLng linkLatLng = new LinkLatLng();
        mLinkList = new ArrayList<>();
        LinkLatLng[] linkLatLngs = new LinkLatLng[10000];
        for(int i = 0; i < mNodeList.size() - 1; i++) {
            //mList = new ArrayList<>();
            mLinkList = new ArrayList<>();
            nodeLat = mNodeList.get(i).lat;
            nodeLng = mNodeList.get(i).lng;
            nodeLatLng = mNodeList.get(i);
            //nodeLat = NodeLatLng.nlist.get(i).lat;
            //nodeLng = NodeLatLng.nlist.get(i).lng;
            for(int j = 0; j < LinkLatLng.llist.size() - 1; j++){
                linkTailLat = LinkLatLng.llist.get(j).coordinate.get(0).latitude;    //index 0이 tail이 맞는지.
                linkTailLng = LinkLatLng.llist.get(j).coordinate.get(0).longitude;
                linkHeadLat = LinkLatLng.llist.get(j).coordinate.get(LinkLatLng.llist.get(j).coordinate.size() - 1).latitude;
                linkHeadLng = LinkLatLng.llist.get(j).coordinate.get(LinkLatLng.llist.get(j).coordinate.size() - 1).longitude;


                //범위 수정하고 a*알고리즘 수행할수있도록.
                if( ((nodeLat - 0.0005 <= linkTailLat &&  linkTailLat <= nodeLat + 0.0005) &&
                        (nodeLng - 0.0005 <= linkTailLng && linkTailLng <= nodeLng + 0.0005)) ){  //또는 nodeLat <= linkTailLat <= nodeLat+0.0001 같은 방식으로
                    LinkLatLng.llist.get(j).setNode(nodeLatLng);
                    mLinkList.add(LinkLatLng.llist.get(j));
                    //index++;
                    //linkLatLng.setNode(nodeLatLng);
                    //mLinkList.add(linkLatLng);
                    //mLinkList.add(linkLatLng);
                    //linkLatLngs[i].setNode(nodeLatLng);
                        //mLinkList.get(0).setNode(mNodeList.get(i));
                          //link에 연결된 모든 node값이 추가가 되는지 확인
                }
                if( ((nodeLat - 0.0005 <= linkHeadLat && linkHeadLat <= nodeLat + 0.0005) &&
                        (nodeLng - 0.0005 <= linkHeadLng && linkHeadLng <= nodeLng + 0.0005)) ){
                    LinkLatLng.llist.get(j).setNextNode(nodeLatLng);
                    mLinkList.add(LinkLatLng.llist.get(j));
                    //index++;
                    //linkLatLng.setNextNode(nodeLatLng);
                    //mLinkList.add(linkLatLng);
                }
                //linkLatLng = new LinkLatLng(LinkLatLng.llist.get(j).link_id, LinkLatLng.llist.get(j).coordinate
                        //, LinkLatLng.llist.get(j).distance);
            }
            for(int k = 0; k < mLinkList.size(); k++){
                linkLatLngs[k].setNode(nodeLatLng);
                linkLatLngs[k].setLink_id(LinkLatLng.llist.get(k).link_id);
                linkLatLngs[k].setCoordinate(LinkLatLng.llist.get(k).coordinate);
                linkLatLngs[k].setDistance(LinkLatLng.llist.get(k).distance);
            }
            //mLinkList.get().setNode();
            mNodeList.get(i).setLink(mLinkList);
            //mNodeList.get(i).setLink(mLinkList);
            //NodeLatLng.nlist.get(i).setLink(mList);
        }
        return mNodeList;
    }

}
