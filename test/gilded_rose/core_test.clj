(ns gilded-rose.core-test
  (:require [clojure.test :refer :all]
            [gilded-rose.core :refer :all]))

(deftest gilded-rose-test
  (testing "For all items except Sulfuras, sell in date decreases by one"
    (let [not-sulfuras (item "not sulfuras" 20 30)
          updated-sell-in 19]
      (is (= updated-sell-in (:sell-in (first (update-quality [not-sulfuras])))))))
  (testing "Sulfuras never ages"
    (let [sulfuras (item "Sulfuras, Hand of Ragnaros" 5 5)
          updated-sell-in 5]
      (is (= updated-sell-in (:sell-in (first (update-quality [sulfuras])))))))
  (testing "For normal items, quality degrades by one before sell by date"
    (let [normal-item (item "+5 Dexterity Vest" 5 5)
          updated-quality 4]
      (is (= updated-quality (:quality (first (update-quality [normal-item])))))))
  (testing "For normal items, quality degrades by two after sell by date"
    (let [normal-item (item "+5 Dexterity Vest" 0 5)
          updated-quality 3]
      (is (= updated-quality (:quality (first (update-quality [normal-item])))))))
  (testing "Aged Brie increases in quality the older it gets"
    (let [aged-brie (item "Aged Brie" 5 5)
          updated-quality 6]
      (is (= updated-quality (:quality (first (update-quality [aged-brie])))))))
  (testing "Aged Brie increases in quality twice as fast after sell by date"
    (let [aged-brie (item "Aged Brie" 0 5)
          updated-quality 7]
      (is (= updated-quality (:quality (first (update-quality [aged-brie])))))))
  (testing "Sulfuras quality never changes"
    (let [sulfuras (item "Sulfuras, Hand of Ragnaros" 5 5)
          updated-quality 5]
      (is (= updated-quality (:quality (first (update-quality [sulfuras])))))))
  (testing "The quality of an item is never more than 50"
    (let [aged-brie (item "Aged Brie" 5 50)
          backstage-passes-far (item "Backstage passes to a TAFKAL80ETC concert" 20 50)
          backstage-passes-close (item "Backstage passes to a TAFKAL80ETC concert" 10 50)
          backstage-passes-very-close (item "Backstage passes to a TAFKAL80ETC concert" 5 50)
          maximum-quality 50]
      (is (= maximum-quality (:quality (first (update-quality [aged-brie])))))
      (is (= maximum-quality (:quality (first (update-quality [backstage-passes-far])))))
      (is (= maximum-quality (:quality (first (update-quality [backstage-passes-close])))))
      (is (= maximum-quality (:quality (first (update-quality [backstage-passes-very-close])))))))
  (testing "The quality of an item is never negative"
    (let [normal-item (item "+5 Dexterity Vest" 5 0)
          updated-quality 0]
      (is (= updated-quality (:quality (first (update-quality [normal-item])))))))
  (testing "Backstage passes increases in quality as sell in date approaches"
    (let [backstage-passes (item "Backstage passes to a TAFKAL80ETC concert" 20 0)
          updated-quality 1]
      (is (= updated-quality (:quality (first (update-quality [backstage-passes])))))))
  (testing "Backstage passes increases in quality by two when sell in is 10 or less"
    (let [backstage-passes (item "Backstage passes to a TAFKAL80ETC concert" 10 0)
          updated-quality 2]
      (is (= updated-quality (:quality (first (update-quality [backstage-passes])))))))
  (testing "Backstage passes increases in quality by three when sell in is 5 or less"
    (let [backstage-passes (item "Backstage passes to a TAFKAL80ETC concert" 5 0)
          updated-quality 3]
      (is (= updated-quality (:quality (first (update-quality [backstage-passes])))))))
  (testing "Backstage passes quality drops to 0 when sell in date has passed"
    (let [backstage-passes (item "Backstage passes to a TAFKAL80ETC concert" 0 30)
          updated-quality 0]
      (is (= updated-quality (:quality (first (update-quality [backstage-passes]))))))))
