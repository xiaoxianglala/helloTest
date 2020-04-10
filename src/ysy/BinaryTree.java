package ysy;

/**
 * @ClassName BinaryTree
 * @Description TODO
 * @Author ysy
 * @Date 2020/4/10 9:35
 **/
public class BinaryTree {
    BNode root = new BNode();
    public BinaryTree(){
        root = null;
    }

    /**
     * @Author ysy
     * @Description //TODO 插入新节点
     * @Date 2020/4/10 10:01
     * @Param [key, data]
     * @return void
     **/
    public void insertBNode(int key,double data){
        //创建新节点，然后赋值
        BNode bNode = new BNode();
        bNode.key = key;
        bNode.data = data;
        if (null == root){
            root = bNode;
        }else {
            //当前节点用于判断是否为空，父节点用于新增子节点
            BNode currrnt = root;
            BNode parent;
            while (true){
                parent = currrnt;
                //判断是新增左节点还是右节点
                if (key < currrnt.key){
                    currrnt = currrnt.leftChild;
                    if (null == currrnt){
                        parent.leftChild = bNode;
                        bNode.paraent = parent;
                        return;
                    }
                }else {
                    currrnt = currrnt.rightChild;
                    if (null == currrnt){
                        parent.rightChild = bNode;
                        bNode.paraent = parent;
                        return;
                    }
                }
            }
        }
    }

    /**
     * @Author ysy
     * @Description //TODO 根据key查询对应节点的数据
     * @Date 2020/4/10 10:06
     * @Param [key]
     * @return java.main.BinaryTree.BNode
     **/
    public BNode findBNode(int key){
        //需要查询的树
        BNode current = root;
            while (current.key != key){
                //key小于当前节点的数据则在左边，否则在右边
                if (key <= current.data){
                    current = current.leftChild;
                }else {
                    current = current.rightChild;
                }
                //如果没有查询到或者需要查询的树为空
                if (null == current){
                    return null;
                }
            }
        return  current;
    }

    /**
     * @Author ysy
     * @Description //TODO 遍历二叉树
     * @Date 2020/4/10 10:12
     * @Param [traverseType] 遍历类型
     * @return void
     **/
    public void traverse(int traverseType){
        switch (traverseType){
            //前序遍历
            case 1:
                System.out.println("Preorder traversal:");
                preOrder(root);
                break;
                //中序遍历
            case 2:
                System.out.println("Inorder traversal:");
                inOrder(root);
                break;
                //后序遍历
            case 3:
                System.out.println("Postorder traversal:");
                postOrder(root);
                break;
                default:
                    System.out.println("Inorder traversal:");
                    inOrder(root);
                    break;
        }
        System.out.println("");
    }

    //后(根)序遍历（左右根）
    private void postOrder(BNode root) {
        if (null != root) {
            postOrder(root.leftChild);
            postOrder(root.rightChild);
            System.out.println(root.data + " ");
        }
    }

   // 中(根)序遍历（左根右）
    private void inOrder(BNode root) {
        if (null != root){
            inOrder(root.leftChild);
            System.out.println(root.data + " ");
            inOrder(root.rightChild);
        }
    }

    //先(根)序遍历（根左右）
    private void preOrder(BNode root) {
        if (null != root){
            System.out.println(root.data + " ");
            preOrder(root.leftChild);
            preOrder(root.rightChild);
        }

    }

    /**
     * @Author ysy
     * @Description //TODO 获得最小值，最小值在树的最左边
     * @Date 2020/4/10 11:11
     * @Param []
     * @return ysy.BinaryTree.BNode
     **/
    public BNode findMinNumber(){
        BNode current = root;
        BNode parent = root;
        while (null != current){
            parent = current;
            current = current.leftChild;
        }
        return parent;
    }

    /**
     * @Author ysy
     * @Description //TODO 查询最大值，最大值在树的最右边
     * @Date 2020/4/10 11:13
     * @Param []
     * @return ysy.BinaryTree.BNode
     **/
    public BNode findMaxNumber(){
        BNode current = root;
        BNode parent = root;
        while (null != current){
            parent = current;
            current = current.rightChild;
        }
        return parent;
    }

    public boolean deleteBNode(int key){
        BNode current = root;
        boolean isLeftChild = true;
        if (null == current){
            return false;
        }
        //寻找要删除的节点
        while (current.key != key){
            if (key < current.key){
                isLeftChild = true;
                current = current.leftChild;
            }else {
                isLeftChild = false;
                current = current.rightChild;
            }
            //要删除的节点不存在
            if (null == current){
                return false;
            }
        }
        //找到了要删除的节点，如果改节点没有左右孩子的话，直接就将改节点复制为null
        if (null == current.leftChild && null == current.rightChild) {
            return deleteNoChild(current, isLeftChild);
        }else if (null != current.leftChild && null != current.rightChild){

            //删除有两个节点的
            return deleteTwoChild(current, isLeftChild);

        }else {

            //删除的节点有一个子节点，直接将其砍断，将其子节点与其父节点连起来即可，
            // 要考虑特殊情况就是删除根节点，因为根节点没有父节点
            return deleteOneChild(current, isLeftChild);

        }

    }

    /**
     * @Author ysy
     * @Description //TODO 删除只有一个孩子节点的节点
     * @Date 2020/4/10 12:00
     * @Param [current, isLeftChild]
     * @return boolean
     **/
    private boolean deleteOneChild(BNode current, boolean isLeftChild) {
        //判断节点的孩子是左节点还是右节点
        if (null == current.leftChild){
            if (current == root){
                root = current.rightChild;
                current.paraent = null;
                return true;
            }
            //如果该节点是左节点
            if (isLeftChild){
                //因为改节点是左节点，删除之后他的孩子需要来当左节点
                current.paraent.leftChild = current.rightChild;
            }else {
                current.paraent.rightChild = current.rightChild;
            }
            current.rightChild.paraent =current.paraent;
        }else {
            if (current == root){
                root = current.leftChild;
                current.paraent = null;
            }
            if (isLeftChild){
                current.paraent.leftChild = current.leftChild;
            }else {
                current.paraent.rightChild = current.leftChild;
            }
            current.leftChild.paraent = current.paraent;
        }
        return true;
    }

    /**
     * @Author ysy
     * @Description //TODO 删除有两个孩子节点的节点
     * @Date 2020/4/10 14:27
     * @Param [current, isLeftChild]
     * @return boolean
     **/
    private boolean deleteTwoChild(BNode current, boolean isLeftChild) {
        BNode successor = getSuccessor(current);
        //如果删除的是根节点，后续节点的的左孩子为根节点的左孩子
        if (current == root){
            successor.leftChild = root.leftChild;
            successor.rightChild = root.rightChild;
            successor.paraent = null;
            root = successor;
        }else if (isLeftChild){
            current.paraent.leftChild = successor;
        }else {
            current.paraent.rightChild = successor;
        }
        successor.leftChild = current.leftChild;
        return true;
    }

    /**
     * @Author ysy
     * @Description //TODO 获得要删除节点的后继节点（中序遍历的下一个节点）
     * @Date 2020/4/10 12:07 
     * @Param [delNode]
     * @return ysy.BinaryTree.BNode
     **/
    private BNode getSuccessor(BNode delNode) {
        BNode successor = delNode;
        //后续节点为要删除节点的有孩子的左孩子的左孩子...直到没有左孩子的左节点
        BNode current = delNode.rightChild;
        //获得后续节点
        while (null != current){
            successor = current;
            current = current.leftChild;
        }
        if (successor != delNode.rightChild){
            //后续节点的父节点的左孩子指向后续节点的右孩子，即是后续节点的位置被他自己的右孩子取代
            successor.paraent.leftChild = successor.rightChild;
            if (null != successor.rightChild){
                successor.rightChild.paraent = successor.paraent;
            }
            //后续节点的右孩子指向删除节点的右孩子
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    /**
     * @Author ysy
     * @Description //TODO 当前需要删除的节点没有子节点，则直接将该节点赋值为null
     * @Date 2020/4/10 11:29
     * @Param [current, isLeftChild]
     * @return boolean
     **/
    private boolean deleteNoChild(BNode current, boolean isLeftChild) {
        //如果只有根节点
        if (current == root){
            root = null;
            return true;
        }
        //如果该节点是左节点
        if (isLeftChild){
            current.paraent.leftChild = null;
        }else {
            current.paraent.rightChild = null;
        }
        return true;
    }

    class BNode{
       public int key;
       public double data;
       public BNode paraent;
       public BNode leftChild;
       public BNode rightChild;

       public void printfNode(){
           System.out.println("{" + key + ";" + data + "}");
       }
    }
    public static void main(String[] args){
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.insertBNode(9,9);
        binaryTree.insertBNode(10,10);
        binaryTree.insertBNode(8,8);
        binaryTree.insertBNode(7,7);
        binaryTree.insertBNode(4,4);
        binaryTree.insertBNode(6,6);
        binaryTree.insertBNode(3,3);
        binaryTree.traverse(2);
        System.out.println("======================");
        System.out.println("最大值为："+binaryTree.findMaxNumber().data);
        System.out.println("最小值为："+binaryTree.findMinNumber().data);
    }
}
