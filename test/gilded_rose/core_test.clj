(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(deftest gilded-rose-test
  (testing "For normal items, quality degrades by one before sell by date"
    (let [normal-item (item "+5 Dexterity Vest" 5 5)
          updated-quality 4]
      (is (= updated-quality (:quality (first (update-quality [normal-item])))))))
  (testing "For all items except Sulfuras, sell in date decreases by one"
    (let [not-sulfuras (item "not sulfuras" 20 30)
          updated-sell-in 19]
      (is (= updated-sell-in (:sell-in (first (update-quality [not-sulfuras])))))))
  (testing "Aged Brie increases in quality the older it gets"
    (let [aged-brie (item "Aged Brie" 5 5)
          updated-quality 6]
      (is (= updated-quality (:quality (first (update-quality [aged-brie])))))))
  (testing "Sulfuras quality never changes"
    (let [sulfuras (item "Sulfuras, Hand of Ragnaros" 5 5)
          updated-quality 5]
      (is (= updated-quality (:quality (first (update-quality [sulfuras])))))))
  (testing "Sulfuras never ages"
    (let [sulfuras (item "Sulfuras, Hand of Ragnaros" 5 5)
          updated-sell-in 5]
      (is (= updated-sell-in (:sell-in (first (update-quality [sulfuras])))))))
  (testing "The quality of an item is never more than 50"
    (let [aged-brie (item "Aged Brie" 5 50)
          updated-quality 50]
      (is (= updated-quality (:quality (first (update-quality [aged-brie]))))))))
