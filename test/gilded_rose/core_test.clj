(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(deftest gilded-rose-test
  (testing "For normal items, quality degrades by one before sell by date"
    (let [normal-item {:name "+5 Dexterity Vest" :sell-in 5 :quality 5}
          updated-quality 4]
      (is (= updated-quality (:quality (first (update-quality [normal-item]))))))))
