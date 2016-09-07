(ns smart-rename.core-test
  (:require
    [smart-rename.core :as core]
    [clojure.java.io :as io]
    [clojure.test :refer [deftest is]]))


(deftest test-ls
  (is (= 2
         (count (core/ls (.getPath (io/resource "test"))))))
  (is (= #{"1.txt" "2.txt"}
         (set (map #(.getName %) (core/ls (.getPath (io/resource "test"))))))))

