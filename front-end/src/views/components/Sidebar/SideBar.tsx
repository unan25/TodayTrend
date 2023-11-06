import React from "react";
import { Accordion, Container, Row, Button } from "react-bootstrap";
import style from "./SideBar.module.css";

function SideBar() {
  return (
    <div>
      <Container>
        <Row>
          <div> profile </div>
        </Row>
        <Row>
          <Accordion>
            <Accordion.Item eventKey="0">
              <Accordion.Header>menu 1</Accordion.Header>
              <Accordion.Body>
                <Button>button1</Button>
                <Button>button2</Button>
                <Button>button3</Button>
              </Accordion.Body>
            </Accordion.Item>
            <Accordion.Item eventKey="1">
              <Accordion.Header>menu 2</Accordion.Header>
              <Accordion.Body>
                <Button>button1</Button>
                <Button>button2</Button>
                <Button>button3</Button>
              </Accordion.Body>
            </Accordion.Item>
            <Accordion.Item eventKey="2">
              <Accordion.Header>menu 3</Accordion.Header>
              <Accordion.Body>
                <Button>button1</Button>
                <Button>button2</Button>
                <Button>button3</Button>
              </Accordion.Body>
            </Accordion.Item>
          </Accordion>
        </Row>
      </Container>
    </div>
  );
}

export default SideBar;
