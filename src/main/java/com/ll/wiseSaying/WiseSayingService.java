package com.ll.wiseSaying;

import java.util.HashMap;

class WiseSayingService {
    HashMap<Integer, WiseSaying> postList;
    int id = 0;

    public WiseSayingService() {
        WiseSayingRepository.createDir();
        id = WiseSayingRepository.getLastId();
        postList = WiseSayingRepository.loadPosts(id);
    }

    // 등록
    public int createPost(String content, String author) {
        WiseSaying newWiseSaying = new WiseSaying(++id, author, content);
        postList.put(id, newWiseSaying);
        // 파일로 저장
        WiseSayingRepository.updateLastId(id);
        WiseSayingRepository.createJsonFile(postList.get(id));
        return id;
    }

    // 삭제
    public void deletePost(int deleteId) {
        postList.remove(deleteId);
        // 파일 삭제
        WiseSayingRepository.deleteJsonFile(deleteId);
    }

    // 수정
    public void updatePost(String content, String author, int updateId) {
        postList.get(updateId).setContent(content);
        postList.get(updateId).setAuthor(author);
        // 파일 업데이트
        WiseSayingRepository.createJsonFile(postList.get(id));

    }


    public boolean existPost(int postId) {
        return postList.containsKey(postId);
    }
    public WiseSaying getPost(int postId) {
        return postList.get(postId);
    }
}
